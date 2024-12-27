package com.escambo.api.marcacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcacaoRepository extends JpaRepository<Marcacao, Long> {
    Page<Marcacao> findAllByAtivoTrue(Pageable paginacao);
}
