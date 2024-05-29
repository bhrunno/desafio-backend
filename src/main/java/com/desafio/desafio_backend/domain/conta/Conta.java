package com.desafio.desafio_backend.domain.conta;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="CONTA", schema = "PUBLIC")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "situacao")
    @Enumerated(EnumType.ORDINAL)
    private SituacaoConta situacaoConta;

    @Override
    public String toString() {
        return String.format(
                "Conta[id=%d, dataVencimento='%s', dataPagamento='%s', valor='%s', descricao='%s', situacao='%s']",
                id, dataVencimento, dataPagamento, valor, descricao, situacaoConta);
    }

    public Long id() {
        return id;
    }

    public Conta setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate dataVencimento() {
        return dataVencimento;
    }

    public Conta setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
        return this;
    }

    public LocalDate dataPagamento() {
        return dataPagamento;
    }

    public Conta setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
        return this;
    }

    public BigDecimal valor() {
        return valor;
    }

    public Conta setValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public SituacaoConta situacaoConta() {
        return situacaoConta;
    }

    public Conta setSituacaoConta(SituacaoConta situacaoConta) {
        this.situacaoConta = situacaoConta;
        return this;
    }

    public String descricao() {
        return descricao;
    }

    public Conta setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public SituacaoConta situacao() {
        return situacaoConta;
    }

    public Conta setSituacao(SituacaoConta situacaoConta) {
        this.situacaoConta = situacaoConta;
        return this;
    }
}
