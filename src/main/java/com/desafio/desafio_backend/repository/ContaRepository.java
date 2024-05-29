package com.desafio.desafio_backend.repository;

import com.desafio.desafio_backend.domain.conta.Conta;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Transactional
    @Modifying
    @Query(value="update Conta c set c.situacao = :situacaoConta where c.id = :id", nativeQuery = true)
    void atualizaSituacaoConta(@Param("situacaoConta") Integer situacaoConta, @Param("id") Long id);

    Page<Conta> findByDataVencimentoAndDescricao(LocalDate vencimento, String descricao, Pageable pageable);

    List<Conta> findByDataPagamentoBetween(LocalDate periodoInicio, LocalDate periodoFim);

}