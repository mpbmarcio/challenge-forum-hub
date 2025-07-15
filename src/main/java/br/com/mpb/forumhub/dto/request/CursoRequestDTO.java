package br.com.mpb.forumhub.dto.request;

import br.com.mpb.forumhub.model.Curso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoRequestDTO(@NotNull Long id,
                              @NotBlank(message = "O nome é obrigatório!") String nome) {
    public CursoRequestDTO(Curso curso) {
        this(curso.getId(), curso.getNome());
    }
}
