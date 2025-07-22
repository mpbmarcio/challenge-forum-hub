package br.com.mpb.forumhub.controller;

import br.com.mpb.forumhub.dto.request.RespostaRequestDTO;
import br.com.mpb.forumhub.dto.response.RespostaResponseDTO;
import br.com.mpb.forumhub.repository.TopicoRepository;
import br.com.mpb.forumhub.service.RespostaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/respostas")
@SecurityRequirement(name = "bearer-key")
public class RespostaController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespostaService respostaService;

    @PostMapping
    public ResponseEntity<RespostaResponseDTO> cadastrar(@RequestBody @Valid RespostaRequestDTO dados) {
        RespostaResponseDTO respostaDTO = respostaService.cadastrar(dados);

        URI uri = URI.create("/respostas/" + respostaDTO.id());

        return ResponseEntity
                .created(uri)
                .body(respostaDTO);
    }

    @GetMapping
    public List<RespostaResponseDTO> listar() {
        return respostaService.listar();
    }
}
