package com.escambo.api.controller;

import com.escambo.api.medico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody DadosCadastroMedico dados, UriComponentsBuilder uribuilder) {
        var medico = new Medico(dados);
        repository.save(medico);
        var url = uribuilder.path("/medicos/{id}").buildAndExpand(medico.getID()).toUri();
        return ResponseEntity.created(url).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public Page<DadosListagemMedicos> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicos::new);
    }

    @GetMapping("/{crm}")
    public DadosListagemMedicos busca(@PathVariable String crm) {
        var medico = repository.findByCrm(crm);
        if (medico == null || medico.getAtivo() != true) {
            throw new IllegalArgumentException("Médico com CRM " + crm + " não encontrado.");
        }
        return new DadosListagemMedicos(medico);
    }

    @Transactional
    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosatualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var medico = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico com id " + id + " não encontrado."));
        repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }

}
