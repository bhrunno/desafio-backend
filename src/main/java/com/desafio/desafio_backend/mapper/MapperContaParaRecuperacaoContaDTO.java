package com.desafio.desafio_backend.mapper;

import com.desafio.desafio_backend.domain.conta.Conta;
import com.desafio.desafio_backend.domain.conta.RecuperacaoContaDTO;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;

@Component
public class MapperContaParaRecuperacaoContaDTO {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RecuperacaoContaDTO handle(Conta conta) {
      return new RecuperacaoContaDTO(
                conta.id(),
                conta.dataVencimento().format(formatter),
                conta.dataPagamento().format(formatter),
                conta.valor(),
                conta.descricao(),
                conta.situacaoConta().getSituacao()
                );
    }
}
