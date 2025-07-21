package br.com.mpb.forumhub.service;

import br.com.mpb.forumhub.dto.request.RespostaRequestDTO;
import br.com.mpb.forumhub.dto.response.RespostaResponseDTO;
import br.com.mpb.forumhub.dto.response.UsuarioResponseDTO;
import br.com.mpb.forumhub.model.Resposta;
import br.com.mpb.forumhub.model.Topico;
import br.com.mpb.forumhub.model.Usuario;
import br.com.mpb.forumhub.repository.RespostaRepository;
import br.com.mpb.forumhub.repository.TopicoRepository;
import br.com.mpb.forumhub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespostaService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespostaRepository respostaRepository;

    public List<RespostaResponseDTO> listar() {
        return respostaRepository.findAll().stream()
                .map(r -> new RespostaResponseDTO(
                        r.getId(),
                        r.getMensagem(),
                        r.getDataInc(),
                        new UsuarioResponseDTO(
                                r.getAutor().getId(),
                                r.getAutor().getNome(),
                                r.getAutor().getEmail())
                )).toList();
    }

    public RespostaResponseDTO cadastrar(RespostaRequestDTO dto) {
        Topico topico = topicoRepository.findById(dto.topicoId())
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        Usuario autor = usuarioRepository.findById(dto.autorId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Resposta resposta = new Resposta(
                dto.mensagem(),
                topico,
                autor
        );

        respostaRepository.save(resposta);

        return new RespostaResponseDTO(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataInc(),
                new UsuarioResponseDTO( resposta.getAutor().getId(),
                        resposta.getAutor().getNome(),
                        resposta.getAutor().getEmail())
        );
    }
}