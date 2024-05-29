package com.desafio.desafio_backend.mapper;

import com.desafio.desafio_backend.domain.conta.AtualizaContaDTO;
import com.desafio.desafio_backend.domain.conta.Conta;
import com.desafio.desafio_backend.domain.conta.SituacaoConta;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class MapperAtualizaContaDTOParaConta {

    private DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Conta handle(AtualizaContaDTO atualizaContaDTO, Conta conta) throws ParseException {
      return conta
              .setId(atualizaContaDTO.id())
              .setDataPagamento(LocalDate.parse(atualizaContaDTO.dataPagamento(), formatters))
              .setDataVencimento(LocalDate.parse(atualizaContaDTO.dataVencimento(),formatters))
              .setDescricao(atualizaContaDTO.descricao())
              .setSituacao(SituacaoConta.of(atualizaContaDTO.situacaoConta()))
              .setValor(atualizaContaDTO.valor());
    }
}
