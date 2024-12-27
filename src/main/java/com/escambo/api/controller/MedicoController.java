package com.escambo.api.controller;

import com.escambo.api.medico.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping
    public Page<DadosListagemMedicos> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicos::new);
    }

    @GetMapping("/{crm}")
    public DadosListagemMedicos busca(@PathVariable String crm) {
        var medico = repository.findByCrm(crm);
        if (medico == null) {
            throw new IllegalArgumentException("Médico com CRM " + crm + " não encontrado.");
        }
        return new DadosListagemMedicos(medico);
    }

    @Transactional
    @PutMapping
    public void atualizar(@RequestBody DadosatualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var medico = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico com id " + id + " não encontrado."));
        repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

}
