package br.com.mpb.forumhub.controller;

import br.com.mpb.forumhub.dto.request.TopicoRequestDTO;
import br.com.mpb.forumhub.dto.response.TopicoResponseDTO;
import br.com.mpb.forumhub.model.Topico;
import br.com.mpb.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public ResponseEntity<List<TopicoResponseDTO>> listar() {
        List<TopicoResponseDTO> topicos = topicoService.listar();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponseDTO> listarId(@PathVariable Long id) {
        TopicoResponseDTO topico = topicoService.listarId(id);

        if (topico == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(topico);
    }

    @PostMapping
    public ResponseEntity<TopicoRequestDTO> cadastrar(@RequestBody @Valid TopicoRequestDTO dados) {
        Topico topico =  topicoService.cadastrar(dados);

        URI uri = URI.create("/topicos/" + topico.getId());

        return ResponseEntity
                .created(uri)
                .body(new TopicoRequestDTO(topico));
    }
}
