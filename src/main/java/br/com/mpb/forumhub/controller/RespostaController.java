package br.com.mpb.forumhub.controller;

import br.com.mpb.forumhub.dto.request.RespostaRequestDTO;
import br.com.mpb.forumhub.dto.response.RespostaResponseDTO;
import br.com.mpb.forumhub.repository.TopicoRepository;
import br.com.mpb.forumhub.service.RespostaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class RespostaController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private RespostaService respostaService;

    @GetMapping("/topicos/{id}/respostas")
    public List<RespostaResponseDTO> listarId(@PathVariable Long id) {
        return respostaService.listar(id);
    }

    @PostMapping("/respostas")
    public ResponseEntity<RespostaResponseDTO> cadastrar(@RequestBody @Valid RespostaRequestDTO dados) {
        RespostaResponseDTO respostaDTO = respostaService.cadastrar(dados);

        URI uri = URI.create("/respostas/" + respostaDTO.id());

        return ResponseEntity
                .created(uri)
                .body(respostaDTO);
    }

    @PostMapping("/topicos/{id}/respostas")
    public ResponseEntity<RespostaResponseDTO> responderTopico(@PathVariable Long id, @RequestBody @Valid RespostaRequestDTO dados) {
        RespostaRequestDTO dadosComTopico = new RespostaRequestDTO(
                null,
                dados.mensagem(),
                id,
                dados.autorId()
        );

        RespostaResponseDTO respostaDTO = respostaService.cadastrar(dadosComTopico);

        URI uri = URI.create("/topicos/" + id + "/respostas/" + respostaDTO.id());
        return ResponseEntity.created(uri).body(respostaDTO);
    }
}
