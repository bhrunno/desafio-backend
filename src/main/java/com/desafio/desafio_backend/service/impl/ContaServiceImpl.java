package com.desafio.desafio_backend.service.impl;

import com.desafio.desafio_backend.domain.conta.*;
import com.desafio.desafio_backend.mapper.MapperAtualizaContaDTOParaConta;
import com.desafio.desafio_backend.mapper.MapperCadastroContaDTOParaConta;
import com.desafio.desafio_backend.mapper.MapperContaParaRecuperacaoContaDTO;
import com.desafio.desafio_backend.repository.ContaRepository;
import com.desafio.desafio_backend.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ContaServiceImpl implements ContaService {

    @Autowired
    private MapperContaParaRecuperacaoContaDTO mapperContaParaRecuperacaoContaDTO;
    @Autowired
    private MapperCadastroContaDTOParaConta mapperCadastroContaDTOParaConta;
    @Autowired
    private MapperAtualizaContaDTOParaConta mapperAtualizaContaDTOParaConta;
    @Autowired
    private ContaRepository contaRepository;

    private DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RecuperacaoContaDTO cadastrarConta(CadastroContaDTO cadastroContaDTO) throws ParseException {
        var conta = mapperCadastroContaDTOParaConta.handle(cadastroContaDTO);
        var contaCadastrado = contaRepository.save(conta);

       return  mapperContaParaRecuperacaoContaDTO.handle(contaCadastrado);
    }

    public RecuperacaoContaDTO atualizarConta(AtualizaContaDTO atualizaContaDTO) throws Exception {
        var conta = contaRepository.findById(atualizaContaDTO.id()).orElseThrow(Exception::new);
        var novaConta = mapperAtualizaContaDTOParaConta.handle(atualizaContaDTO, conta);
        var contaAtualizado = contaRepository.save(novaConta);

        return  mapperContaParaRecuperacaoContaDTO.handle(contaAtualizado);
    }

    public RecuperacaoContaDTO atualizarSituacaoConta(Long idConta, int situacao) throws Exception {
        var conta = contaRepository.findById(idConta).orElseThrow(Exception::new);
        var novaConta = conta.setSituacao(SituacaoConta.of(situacao));
        var contaAtualizado = contaRepository.save(novaConta);

        return  mapperContaParaRecuperacaoContaDTO.handle(contaAtualizado);
    }

    public RecuperacaoContaDTO consultarContaId(Long idConta) throws Exception {
        var conta = contaRepository.findById(idConta).orElseThrow(Exception::new);
        return  mapperContaParaRecuperacaoContaDTO.handle(conta);
    }

    public Page<RecuperacaoContaDTO> listaContaApagar(String vencimento, String descricao, Pageable pageable) throws ParseException {
        var contaPage = contaRepository.findByDataVencimentoAndDescricao(LocalDate.parse(vencimento, formatters), descricao, pageable);
        return contaPage.map(conta ->  mapperContaParaRecuperacaoContaDTO.handle(conta));
    }

    public BigDecimal valorTotalPagoPeriodo(String periodoInicio, String periodoFim) throws ParseException {
        LocalDate datePeriodoInicio = LocalDate.parse(periodoInicio, formatters);
        LocalDate dateperiodoFim = LocalDate.parse(periodoFim, formatters);
        var listaContaPago = contaRepository.findByDataPagamentoBetween(datePeriodoInicio, dateperiodoFim);

        var valorTotal = listaContaPago.stream()
                .map(conta -> conta.valor())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return valorTotal;
    }
}
