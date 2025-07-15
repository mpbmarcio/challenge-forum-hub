package br.com.mpb.forumhub.controller;

import br.com.mpb.forumhub.dto.request.CursoRequestDTO;
import br.com.mpb.forumhub.dto.response.CursoResponseDTO;
import br.com.mpb.forumhub.model.Curso;
import br.com.mpb.forumhub.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoResponseDTO>> listar() {
        List<CursoResponseDTO> cursos = cursoService.listar();
        return ResponseEntity.ok(cursos);
    }

    @PostMapping
    public ResponseEntity<CursoRequestDTO> cadastrar(@RequestBody @Valid CursoRequestDTO dados) {
        Curso curso = cursoService.cadastrar(dados);

        URI uri = URI.create("/cursos/" + curso.getId());

        return ResponseEntity
                .created(uri)
                .body(new CursoRequestDTO(curso));
    }
}
