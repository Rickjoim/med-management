package com.escambo.api.controller;

import com.escambo.api.marcacao.*;
import com.escambo.api.medico.DadosListagemMedicos;
import com.escambo.api.medico.Medico;
import com.escambo.api.medico.MedicoRepository;
import com.escambo.api.paciente.DadosDetalhamentoPaciente;
import com.escambo.api.paciente.Paciente;
import com.escambo.api.paciente.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marcacoes")
public class MarcacaoController {

    @Autowired
    private MarcacaoRepository marcacaoRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> criarMarcacao(@RequestBody DadosCadastroMarcacao dados) {
        Medico medico = medicoRepository.findById(dados.medicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado"));
        Paciente paciente = pacienteRepository.findById(dados.pacienteId())
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

        Marcacao marcacao = new Marcacao(dados, medico, paciente);
        marcacaoRepository.save(marcacao);

        URI uri = URI.create("/marcacoes/" + marcacao.getId());
        return ResponseEntity.created(uri).body(marcacao);
    }

    @GetMapping
    public Page<DadosListagemMarcacao> listar(@PageableDefault(size = 10) Pageable paginacao) {
        return  marcacaoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMarcacao::new);
    }

    @GetMapping("/{id}")
    public DadosListagemMarcacao buscarPorId(@PathVariable Long id) {
        var marcacao = marcacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marcação com id " + id + " não encontrado."));

        if(marcacao.getAtivo()){
            return new DadosListagemMarcacao(marcacao);
        }else{
            throw new IllegalArgumentException("Marcação não encontrada");
        }


    }


    @PutMapping
    @Transactional
    public void atualizarMarcacao(@RequestBody DadosAtualizacaoMarcacao dados) {
        var marcacao = marcacaoRepository.getReferenceById(dados.id());
        marcacao.atualizarInformacoes(dados);
    }


    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var marcacao = marcacaoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marcação com id " + id + " não encontrada."));
        marcacaoRepository.getReferenceById(id);
        marcacao.excluir();
        return ResponseEntity.noContent().build();
    }
}
