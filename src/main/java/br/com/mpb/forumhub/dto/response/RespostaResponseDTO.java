package br.com.mpb.forumhub.dto.response;

import java.time.LocalDateTime;

public record RespostaResponseDTO(Long id,
                                  String mensagem,
                                  LocalDateTime dataInc,
                                  UsuarioResponseDTO autor) {
}

