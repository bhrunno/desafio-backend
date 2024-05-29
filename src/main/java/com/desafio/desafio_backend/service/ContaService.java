package com.desafio.desafio_backend.service;

import com.desafio.desafio_backend.domain.conta.AtualizaContaDTO;
import com.desafio.desafio_backend.domain.conta.CadastroContaDTO;
import com.desafio.desafio_backend.domain.conta.RecuperacaoContaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.text.ParseException;

public interface ContaService {

    RecuperacaoContaDTO cadastrarConta(CadastroContaDTO cadastroContaDTO) throws ParseException;
    RecuperacaoContaDTO atualizarConta(AtualizaContaDTO atualizaContaDTO) throws Exception;
    RecuperacaoContaDTO atualizarSituacaoConta(Long idConta, int situacao) throws Exception;
    Page<RecuperacaoContaDTO> listaContaApagar(String vencimento, String descricao, Pageable pageable) throws ParseException;
    RecuperacaoContaDTO consultarContaId(Long idConta) throws Exception;
    BigDecimal valorTotalPagoPeriodo(String periodoInicio, String periodoFim) throws ParseException;
}
