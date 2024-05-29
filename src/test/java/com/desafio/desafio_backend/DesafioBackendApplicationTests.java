package com.desafio.desafio_backend;

import com.desafio.desafio_backend.domain.conta.*;
import com.desafio.desafio_backend.mapper.MapperCadastroContaDTOParaConta;
import com.desafio.desafio_backend.repository.ContaRepository;
import com.desafio.desafio_backend.service.ContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class DesafioBackendApplicationTests {

	@Autowired
	private MapperCadastroContaDTOParaConta mapperCadastroContaDTOParaConta;

	@Test
	void contextLoads() {
	}

	@SpyBean
	private ContaRepository repository;

	@SpyBean
	private ContaService service;

	private Conta conta1;
	private Conta conta2;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		conta1 = new Conta();
		conta1.setId(1L);
		conta1.setDataVencimento(LocalDate.now().plusDays(10));
		conta1.setDataPagamento(LocalDate.now().plusDays(5));
		conta1.setValor(BigDecimal.valueOf(100.00));
		conta1.setDescricao("Conta Ativa");
		conta1.setSituacao(SituacaoConta.ATIVO);
		repository.save(conta1);

		conta2 = new Conta();
		conta2.setId(2L);
		conta2.setDataVencimento(LocalDate.now().plusDays(10));
		conta2.setDataPagamento(LocalDate.now().plusDays(15));
		conta2.setValor(BigDecimal.valueOf(200.00));
		conta2.setDescricao("Conta Ativa");
		conta2.setSituacao(SituacaoConta.ATIVO);
		repository.save(conta2);
	}

	@Test
	void testGetAllContas() throws ParseException {
		PageRequest pageable = PageRequest.of(0, 10);
		var dataVencimento = conta1.dataVencimento().format(formatter);
		Page<RecuperacaoContaDTO> result = service.listaContaApagar(dataVencimento, conta1.descricao(), pageable);

		assertNotNull(result);
		assertEquals(3, result.getTotalElements());
	}

	@Test
	void testGetContaById() throws Exception {
		when(repository.findById(1L)).thenReturn(Optional.of(conta1));

		Optional<RecuperacaoContaDTO> result = Optional.ofNullable(service.consultarContaId(1L));
		assertTrue(result.isPresent());
		assertEquals("Conta Ativa", result.get().descricao());
	}

	@Test
	void testCreateConta() throws ParseException {

		var cadastroContaDTO = new CadastroContaDTO(
				conta1.dataVencimento().format(formatter),
				conta1.dataPagamento().format(formatter),
				conta1.valor(),
				conta1.descricao(),
				conta1.situacaoConta().getSituacao()
		);

		var result = service.cadastrarConta(cadastroContaDTO);
		assertNotNull(result);
		assertEquals("Conta Ativa", result.descricao());
	}

	@Test
	void testUpdateConta() throws Exception {
		var conta = service.consultarContaId(1L);

		var atualizaContaDTO = new AtualizaContaDTO(
				conta.id(),
				conta.dataVencimento(),
				conta.dataPagamento(),
				conta.valor(),
				conta.descricao(),
				conta.situacaoConta()
		);

		RecuperacaoContaDTO result = service.atualizarConta(atualizaContaDTO);
		assertNotNull(result);
		assertEquals("Conta Ativa", result.descricao());
	}

	@Test
	void testFilterContas() {
		PageRequest pageable = PageRequest.of(0, 10);
		var listaConta = repository.findByDataVencimentoAndDescricao(conta1.dataVencimento(), conta1.descricao(), pageable);
		var listaContaPaga = repository.findByDataPagamentoBetween(conta1.dataPagamento(), conta2.dataPagamento());


		assertNotNull(listaConta);
		assertNotNull(listaContaPaga);
		assertEquals(2, listaConta.getTotalElements());
		assertEquals(2, listaContaPaga.size());
	}

	@Test
	void testGetTotalPagoByPeriodo() throws ParseException {
		BigDecimal result = service.valorTotalPagoPeriodo(conta1.dataPagamento().format(formatter), conta2.dataPagamento().format(formatter));
		assertNotNull(result);
		var valorEsperado = new BigDecimal("300.00");
		assertEquals(valorEsperado, result);
	}
}



