package com.escambo.api.controller;

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

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository repository;
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroPaciente dados){
        repository.save(new Paciente(dados));
    }


    @GetMapping
    public Page<DadosListagemPacientes> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemPacientes::new);
    }

    @GetMapping("/{nome}")
    public DadosListagemPacientes busca(@PathVariable String nome) {
        var paciente = repository.findByNome(nome);
        if (paciente == null) {
            throw new IllegalArgumentException("Paciente com nome " + nome + " não encontrado.");
        }
        return new DadosListagemPacientes(paciente);
    }

    @Transactional
    @PutMapping
    public void atualizar(@RequestBody DadosatualizacaoPaciente dados){
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        var paciente = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente com id " + id + " não encontrado."));
        repository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

}
