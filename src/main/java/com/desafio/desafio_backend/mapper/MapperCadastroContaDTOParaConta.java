package com.desafio.desafio_backend.mapper;

import com.desafio.desafio_backend.domain.conta.CadastroContaDTO;
import com.desafio.desafio_backend.domain.conta.Conta;
import com.desafio.desafio_backend.domain.conta.SituacaoConta;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class MapperCadastroContaDTOParaConta {

    private DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Conta handle(CadastroContaDTO cadastroContaDTO) throws ParseException {
        return new Conta()
                .setDataPagamento(LocalDate.parse(cadastroContaDTO.dataPagamento(), formatters))
                .setDataVencimento(LocalDate.parse(cadastroContaDTO.dataVencimento(),formatters))
                .setDescricao(cadastroContaDTO.descricao())
                .setSituacao(SituacaoConta.of(cadastroContaDTO.situacaoConta()))
                .setValor(cadastroContaDTO.valor());
    }
}