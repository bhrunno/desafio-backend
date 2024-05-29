package com.desafio.desafio_backend.controller;

import com.desafio.desafio_backend.domain.usuario.AutenticacaoDTO;
import com.desafio.desafio_backend.domain.usuario.LoginResponseDTO;
import com.desafio.desafio_backend.domain.usuario.RegistroDTO;
import com.desafio.desafio_backend.domain.usuario.Usuario;
import com.desafio.desafio_backend.infra.security.TokenService;
import com.desafio.desafio_backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/cadastra-usuario")
    public ResponseEntity register(@RequestBody @Valid RegistroDTO data){
        if(usuarioService.consultaUsuarioLoginDetails(data.login()) != null) return ResponseEntity.badRequest().build();

        var encryptedPassword = new BCryptPasswordEncoder().encode(data.senha());
        var novoUsuario = new Usuario(data.login(), encryptedPassword, data.papel());

        usuarioService.salvaUsuario(novoUsuario);

        return ResponseEntity.ok().build();
    }
}
