package br.com.mpb.forumhub.service;

import br.com.mpb.forumhub.dto.request.CursoRequestDTO;
import br.com.mpb.forumhub.dto.request.TopicoRequestDTO;
import br.com.mpb.forumhub.dto.response.CursoResponseDTO;
import br.com.mpb.forumhub.dto.response.TopicoResponseDTO;
import br.com.mpb.forumhub.dto.response.UsuarioResponseDTO;
import br.com.mpb.forumhub.model.Curso;
import br.com.mpb.forumhub.model.Status;
import br.com.mpb.forumhub.model.Topico;
import br.com.mpb.forumhub.model.Usuario;
import br.com.mpb.forumhub.repository.CursoRepository;
import br.com.mpb.forumhub.repository.TopicoRepository;
import br.com.mpb.forumhub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursoRepository cursoRepository;

    public Topico cadastrar(TopicoRequestDTO dados) {
        System.out.println("DTO recebido: " + dados);

        Usuario autor = usuarioRepository.findById(dados.autor().id())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Curso curso = cursoRepository.findById(dados.curso().id())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        Topico topico = new Topico(
                dados.titulo(),
                dados.mensagem(),
                LocalDateTime.now(),
                Status.NAO_RESPONDIDO,
                autor,
                curso);

        return topicoRepository.save(topico);
    }

    public Page<TopicoResponseDTO> listar(Pageable pageable) {
        return topicoRepository.findAll(pageable)
                .map(t -> new TopicoResponseDTO(
                        t.getId(),
                        t.getTitulo(),
                        t.getMensagem(),
                        t.getStatus(),
                        t.getDataInc(),
                        new UsuarioResponseDTO(
                                t.getAutor().getId(),
                                t.getAutor().getNome(),
                                t.getAutor().getEmail()),
                        new CursoResponseDTO(
                                t.getCurso().getId(),
                                t.getCurso().getNome())));
    }

    public List<TopicoResponseDTO> listarTop10OrderByDataIncAsc() {
        return topicoRepository.findTop10ByOrderByDataIncAsc();
    }

    public TopicoResponseDTO listarId(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            return new TopicoResponseDTO(
                    topico.get().getId(),
                    topico.get().getTitulo(),
                    topico.get().getMensagem(),
                    topico.get().getStatus(),
                    topico.get().getDataInc(),
                    new UsuarioResponseDTO(
                            topico.get().getAutor().getId(),
                            topico.get().getAutor().getNome(),
                            topico.get().getAutor().getEmail()),
                    new CursoResponseDTO(
                            topico.get().getCurso().getId(),
                            topico.get().getCurso().getNome()));
        }
        return null;
    }

    public Page<TopicoResponseDTO> buscarPorCursoEAno(String curso, int ano, Pageable pageable) {
        return topicoRepository.buscarPorCursoEAno(curso, ano, pageable)
                .map(t ->
                        new TopicoResponseDTO(
                            t.getId(),
                            t.getTitulo(),
                            t.getMensagem(),
                            t.getStatus(),
                            t.getDataInc(),
                            new UsuarioResponseDTO(
                                    t.getAutor().getId(),
                                    t.getAutor().getNome(),
                                    t.getAutor().getEmail()),
                                    new CursoResponseDTO(
                                            t.getCurso().getId(),
                                            t.getCurso().getNome())
                ));
    }
}
