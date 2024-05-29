package com.desafio.desafio_backend.domain.conta;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;

public record CadastroContaDTO(

        @NotBlank(message = "{not.blank.message}")
        String dataVencimento,
        @NotBlank(message = "{not.blank.message}")
        String dataPagamento,
        @NotBlank(message = "{not.blank.message}")
        BigDecimal valor,
        @NotBlank(message = "{not.blank.message}")
        String descricao,
        @NotBlank(message = "{not.blank.message}")
        int situacaoConta
) {
}
