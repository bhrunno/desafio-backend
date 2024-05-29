package com.desafio.desafio_backend.domain.conta;

public enum SituacaoConta {
    INATIVO(0),
    ATIVO(1);

    private final int situacao;

    private SituacaoConta(int situacao) {
        this.situacao = situacao;
    }

    public int getSituacao() {
        return situacao;
    }

    public static SituacaoConta of(int situacao) {
        switch (situacao) {
            case 0: return INATIVO;
            case 1: return ATIVO;
        }
        return null;
    }

}
