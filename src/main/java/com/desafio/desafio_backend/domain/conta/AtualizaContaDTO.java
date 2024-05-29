package com.desafio.desafio_backend.domain.conta;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record AtualizaContaDTO(

        @NotBlank(message = "{not.blank.message}")
        Long id,
        @NotBlank(message = "{not.blank.message}")
        String dataVencimento,
        @NotBlank(message = "{not.blank.message}")
        String dataPagamento,
        @NotBlank(message = "{not.blank.message}")
        BigDecimal valor,
        @NotBlank(message = "{not.blank.message}")
        String descricao,
        @NotBlank(message = "{not.blank.message}")
        int situacaoConta) {
}
