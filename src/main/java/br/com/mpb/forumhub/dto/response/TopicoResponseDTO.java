package br.com.mpb.forumhub.dto.response;

import br.com.mpb.forumhub.dto.request.CursoRequestDTO;
import br.com.mpb.forumhub.model.Status;

import java.time.LocalDateTime;

public record TopicoResponseDTO(Long id,
                                String titulo,
                                String mensagem,
                                Status status,
                                LocalDateTime dataInc,
                                UsuarioResponseDTO autor,
                                CursoResponseDTO curso) {
}
