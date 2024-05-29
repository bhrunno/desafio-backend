 package com.desafio.desafio_backend.service.impl;

 import com.desafio.desafio_backend.domain.usuario.Usuario;
 import com.desafio.desafio_backend.repository.UsuarioRepository;
 import com.desafio.desafio_backend.service.UsuarioService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.core.userdetails.UserDetails;
 import org.springframework.stereotype.Service;

 @Service
 public class UsuarioServiceImpl implements UsuarioService {

     @Autowired
     private UsuarioRepository usuarioRepository;

     public UserDetails consultaUsuarioLoginDetails(String login) {
         return usuarioRepository.findByLogin(login);
     }

     public Usuario salvaUsuario(Usuario usuario) {
         return usuarioRepository.save(usuario);
     }
 }
