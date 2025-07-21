package br.com.mpb.forumhub.dto.response;

public record LoginResponseDTO(String token,
                               UsuarioResponseDTO usuario) {
}
