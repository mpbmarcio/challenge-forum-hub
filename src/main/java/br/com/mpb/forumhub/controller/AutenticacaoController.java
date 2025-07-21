package br.com.mpb.forumhub.controller;

import br.com.mpb.forumhub.dto.request.DadosTokenJWT;
import br.com.mpb.forumhub.dto.request.UsuarioAutenticacao;
import br.com.mpb.forumhub.dto.response.LoginResponseDTO;
import br.com.mpb.forumhub.dto.response.UsuarioResponseDTO;
import br.com.mpb.forumhub.model.Usuario;
import br.com.mpb.forumhub.service.TokenService;
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

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> efetuarLogin(@RequestBody @Valid UsuarioAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        String tokenJWT = tokenService.gerarToken(usuarioLogado);

        var usuarioDTO = new UsuarioResponseDTO(
                usuarioLogado.getId(),
                usuarioLogado.getNome(),
                usuarioLogado.getEmail()
        );

        var dto = new LoginResponseDTO(tokenJWT, usuarioDTO);

        return ResponseEntity.ok(dto);
    }
}
