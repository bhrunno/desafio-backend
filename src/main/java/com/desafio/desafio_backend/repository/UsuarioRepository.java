package com.desafio.desafio_backend.repository;

import com.desafio.desafio_backend.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    UserDetails findByLogin(String login);
}
