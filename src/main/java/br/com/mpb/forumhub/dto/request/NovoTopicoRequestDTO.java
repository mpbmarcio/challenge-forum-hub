package br.com.mpb.forumhub.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NovoTopicoRequestDTO(
        @NotBlank(message = "O título é obrigatório!") String titulo,
        @NotBlank(message = "A mensagem é obrigatória!") String mensagem,
        @NotBlank(message = "O nome do curso é obrigatório!") String curso
) { }