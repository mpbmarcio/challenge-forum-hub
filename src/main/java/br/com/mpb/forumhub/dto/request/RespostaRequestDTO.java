package br.com.mpb.forumhub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespostaRequestDTO(Long id,
                                 @NotBlank String mensagem,
                                 @NotNull Long topicoId,
                                 @NotNull Long autorId) {
}
