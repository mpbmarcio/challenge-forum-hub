package br.com.mpb.forumhub.service;

import br.com.mpb.forumhub.dto.request.UsuarioRequestDTO;
import br.com.mpb.forumhub.dto.response.UsuarioResponseDTO;
import br.com.mpb.forumhub.model.Usuario;
import br.com.mpb.forumhub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponseDTO(u.getId(),u.getNome(),u.getEmail())).toList();
    }

    public Usuario cadastrar(UsuarioRequestDTO dados) {
        String senha = dados.senha();
        String senhaCriptografada = passwordEncoder.encode(senha);

        Usuario usuario = new Usuario(dados.nome(), dados.email(), senhaCriptografada);
        return usuarioRepository.save(usuario);
    }
}
