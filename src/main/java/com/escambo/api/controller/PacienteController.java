package com.escambo.api.controller;

import com.escambo.api.medico.DadosDetalhamentoMedico;
import com.escambo.api.paciente.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody DadosCadastroPaciente dados, UriComponentsBuilder uribuilder){
        var paciente = new Paciente(dados);
        repository.save(paciente);
        var url = uribuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(new DadosDetalhamentoPaciente(paciente));
    }


    @GetMapping
    public Page<DadosListagemPacientes> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemPacientes::new);
    }

    @GetMapping("/{nome}")
    public DadosListagemPacientes busca(@PathVariable String nome) {
        var paciente = repository.findByNome(nome);
        if (paciente == null || paciente.getAtivo() != true) {
            throw new IllegalArgumentException("Paciente com nome " + nome + " não encontrado.");
        }
        return new DadosListagemPacientes(paciente);
    }

    @Transactional
    @PutMapping
    public ResponseEntity atualizar(@RequestBody DadosatualizacaoPaciente dados){
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var paciente = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente com id " + id + " não encontrado."));
        repository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();
    }

}
