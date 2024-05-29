package com.desafio.desafio_backend.service;

import com.desafio.desafio_backend.domain.usuario.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioService {

    UserDetails consultaUsuarioLoginDetails(String login);
    Usuario salvaUsuario(Usuario usuario);
}
