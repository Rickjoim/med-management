package com.escambo.api.paciente;

import com.escambo.api.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Paciente findByNome(String nome);

    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);
}
