package com.desafio.desafio_backend.service;

import com.desafio.desafio_backend.domain.conta.Conta;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

public interface ArquivoService {

    boolean hasCSVFormat(MultipartFile file);
    List<Conta> csvParaConta(InputStream is) throws ParseException;
    void save(MultipartFile file);
}
