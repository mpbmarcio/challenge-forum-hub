package br.com.mpb.forumhub.controller;

import br.com.mpb.forumhub.dto.request.UsuarioAutenticacao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid UsuarioAutenticacao dados) {
        var token = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        Authentication authentication = authenticationManager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
