package br.com.mpb.forumhub.controller;

import br.com.mpb.forumhub.dto.request.UsuarioRequestDTO;
import br.com.mpb.forumhub.dto.response.UsuarioResponseDTO;
import br.com.mpb.forumhub.model.Usuario;
import br.com.mpb.forumhub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listar();

        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioRequestDTO dados) {
        Usuario usuario = usuarioService.cadastrar(dados);

        URI uri = URI.create("/usuarios/" + usuario.getId());

        return ResponseEntity
                .created(uri)
                .body(new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getEmail()));
    }
}
