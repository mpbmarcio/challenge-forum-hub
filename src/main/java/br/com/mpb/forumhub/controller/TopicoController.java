package br.com.mpb.forumhub.controller;

import br.com.mpb.forumhub.dto.request.AtualizarRequestStatusDTO;
import br.com.mpb.forumhub.dto.request.RespostaRequestDTO;
import br.com.mpb.forumhub.dto.request.TopicoRequestDTO;
import br.com.mpb.forumhub.dto.response.RespostaResponseDTO;
import br.com.mpb.forumhub.dto.response.TopicoResponseDTO;
import br.com.mpb.forumhub.model.Topico;
import br.com.mpb.forumhub.service.RespostaService;
import br.com.mpb.forumhub.service.TopicoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private RespostaService respostaService;

    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listar(@PageableDefault(size = 10) Pageable pageable) {
        Page<TopicoResponseDTO> page = topicoService.listar(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<TopicoResponseDTO>> listarTop10OrderByDataIncAsc(){
        List<TopicoResponseDTO> topicos = topicoService.listarTop10OrderByDataIncAsc();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<Page<TopicoResponseDTO>> buscarPorCursoEAno(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam String curso,
            @RequestParam int ano) {
        Page<TopicoResponseDTO> page = topicoService.buscarPorCursoEAno(curso, ano, pageable);
        return ResponseEntity.ok(page);
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
    public ResponseEntity<TopicoResponseDTO> cadastrar(@RequestBody @Valid TopicoRequestDTO dados) {

    System.out.println("Chegou no controller cadastro");
        Topico topico =  topicoService.cadastrar(dados);

        URI uri = URI.create("/topicos/" + topico.getId());

        return ResponseEntity
                .created(uri)
                .body(new TopicoResponseDTO(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoResponseDTO> atualizar(@RequestBody TopicoRequestDTO dados, @PathVariable Long id) {
        Topico topico = topicoService.buscarTopico(id);
        topico.atualizar(dados);

        return ResponseEntity.ok(new TopicoResponseDTO(topico));
    }

    @PutMapping("{id}/status")
    @Transactional
    public ResponseEntity<TopicoResponseDTO> atualizarStatus(@RequestBody AtualizarRequestStatusDTO dados, @PathVariable Long id) {
        Topico topico = topicoService.atualizarStatus(id, dados);
        return ResponseEntity.ok(new TopicoResponseDTO(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        topicoService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/respostas")
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
