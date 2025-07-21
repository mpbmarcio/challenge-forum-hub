package br.com.mpb.forumhub.dto.request;

import br.com.mpb.forumhub.dto.response.CursoResponseDTO;
import br.com.mpb.forumhub.model.Status;
import br.com.mpb.forumhub.model.Topico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TopicoRequestDTO(Long id,
                               @NotBlank(message = "O título é obrigatório!") String titulo,
                               @NotBlank(message = "A mensagem é obrigatória!") String mensagem,
                               @NotNull Status status,
                               @NotNull LocalDateTime dataInc,
                               UsuarioIdRequestDTO autor,
                               @NotNull(message = "O curso é obrigatório!") CursoResponseDTO curso) {
}
