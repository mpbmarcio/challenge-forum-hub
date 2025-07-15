package br.com.mpb.forumhub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(Long id,
                                @NotBlank String nome,
                                @Email String email,
                                @NotBlank @Size(min = 6, max = 10) String senha) {
}
