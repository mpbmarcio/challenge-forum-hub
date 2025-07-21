package br.com.mpb.forumhub.service;

import br.com.mpb.forumhub.dto.request.CursoRequestDTO;
import br.com.mpb.forumhub.dto.response.CursoResponseDTO;
import br.com.mpb.forumhub.model.Curso;
import br.com.mpb.forumhub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<CursoResponseDTO> listar() {
        return cursoRepository.findAll().stream()
                .map(c -> new CursoResponseDTO(c.getId(), c.getNome())).toList();
    }

    public Curso cadastrar(CursoRequestDTO dados) {
        return cursoRepository.save(new Curso(dados.nome()));
    }

    public void excluir(Long id) {
        cursoRepository.findById(id)
                .ifPresentOrElse(
                        cursoRepository::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"); }
                );
    }
}
