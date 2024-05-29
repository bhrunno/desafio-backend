package com.desafio.desafio_backend.controller;

import com.desafio.desafio_backend.domain.conta.*;
import com.desafio.desafio_backend.service.ContaService;
import com.desafio.desafio_backend.service.ArquivoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.text.ParseException;

@RestController
@RequestMapping("/api/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private ArquivoService arquivoService;

    @PostMapping
    public ResponseEntity<RecuperacaoContaDTO> cadastraConta(@RequestBody CadastroContaDTO cadastroContaDTO) throws ParseException {
        return new ResponseEntity<>(contaService.cadastrarConta(cadastroContaDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RecuperacaoContaDTO> atualizaConta(@Valid @RequestBody AtualizaContaDTO atualizaContaDTO) throws Exception {
        return new ResponseEntity<>(contaService.atualizarConta(atualizaContaDTO), HttpStatus.OK);
    }

    @PutMapping("/atualiza-situacao")
    public ResponseEntity<RecuperacaoContaDTO> atualizaSituacaoConta(
            @RequestParam("idConta") Long idConta, @RequestParam("situacao") int situacao) throws Exception {

        return new ResponseEntity<>(contaService.atualizarSituacaoConta(idConta, situacao), HttpStatus.OK);
    }

    @GetMapping("/lista-conta-apagar")
    public ResponseEntity<Page<RecuperacaoContaDTO>> listaContaApagar(
           @RequestParam("vencimento") String vencimento,
           @RequestParam("descricao") String descricao,
           @PageableDefault(size = 5)
           @SortDefault.SortDefaults({
                   @SortDefault(sort = "dataVencimento", direction = Sort.Direction.ASC),
                   @SortDefault(sort = "id", direction = Sort.Direction.ASC)})
           Pageable pageable) throws ParseException {

        return new ResponseEntity<>(contaService.listaContaApagar(vencimento, descricao, pageable), HttpStatus.OK);
    }

    @GetMapping("/{idConta}")
    public ResponseEntity<RecuperacaoContaDTO> consultaContaId(@PathVariable Long idConta) throws Exception {
        return new ResponseEntity<>(contaService.consultarContaId(idConta), HttpStatus.OK);
    }

    @GetMapping("/valor-total-pago")
    public ResponseEntity<String> valorTotalPagoPeriodo(@RequestParam("periodoInicio") String periodoInicio,
                                                        @RequestParam("periodoFim") String periodoFim) throws ParseException {

       var valorTotalPago = contaService.valorTotalPagoPeriodo(periodoInicio, periodoFim);

        return ResponseEntity.ok("Valor total pago: " + valorTotalPago +
                "\n periodo Inicio: " + periodoInicio + "\n periodo Fim: " + periodoFim);
    }

    @PostMapping("/importa-conta-csv")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (arquivoService.hasCSVFormat(file)) {
            try {
                arquivoService.save(file);

                message = "Importacao do arquivo com sucesso: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Nao foi adicionado arquivo: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Adicione arquivo com extensao CSV!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }


}
