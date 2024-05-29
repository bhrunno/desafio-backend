package com.desafio.desafio_backend.service.impl;

import com.desafio.desafio_backend.domain.conta.Conta;
import com.desafio.desafio_backend.domain.conta.SituacaoConta;
import com.desafio.desafio_backend.repository.ContaRepository;
import com.desafio.desafio_backend.service.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArquivoServiceImpl implements ArquivoService {

     String TYPE = "text/csv";
     String[] HEADERs = { "DataPagamento", "DataVencimento", "Descricao", "Situacao", "Valor" };

    @Autowired
    private ContaRepository contaRepository;

    public boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public List<Conta> csvParaConta(InputStream is) throws ParseException {

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Conta> litaConta = new ArrayList<Conta>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            int contador = 0;
            for (CSVRecord csvRecord : csvRecords) {

                String[] record = csvRecord.get(contador).split(";");

                var conta = new Conta()
                        .setDataPagamento(LocalDate.parse(record[0], formatters))
                        .setDataVencimento(LocalDate.parse(record[1], formatters))
                        .setDescricao(record[2])
                        .setSituacao(SituacaoConta.of(Integer.parseInt(record[3])))
                        .setValor(BigDecimal.valueOf(Long.parseLong(record[4])));

                litaConta.add(conta);
                contador = contador + 1;

            }

            return litaConta;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao realizar o parse CSV: " + e.getMessage());
        }
    }

    public void save(MultipartFile file) {
        try {
            List<Conta> listaConta = csvParaConta(file.getInputStream());
            contaRepository.saveAll(listaConta);
        } catch (IOException | ParseException e) {
            throw new RuntimeException("falha ao salvar informacoes do CSV: " + e.getMessage());
        }
    }

}
