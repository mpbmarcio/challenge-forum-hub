package br.com.mpb.forumhub.dto.response;

import br.com.mpb.forumhub.model.Resposta;

import java.time.LocalDateTime;

public record RespostaResponseDTO(Long id,
                                  String mensagem,
                                  LocalDateTime dataInc,
                                  UsuarioResponseDTO autor) {
    public RespostaResponseDTO(Resposta resposta) {
        this(resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataInc(),
                new UsuarioResponseDTO(
                        resposta.getAutor().getId(),
                        resposta.getAutor().getNome(),
                        resposta.getAutor().getEmail()));
    }
}

