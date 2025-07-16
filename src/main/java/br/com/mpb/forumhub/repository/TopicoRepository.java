package br.com.mpb.forumhub.repository;

import br.com.mpb.forumhub.dto.response.TopicoResponseDTO;
import br.com.mpb.forumhub.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<TopicoResponseDTO> findTop10ByOrderByDataIncAsc();

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :curso AND FUNCTION('YEAR', t.dataInc) = :ano")
    Page<Topico> buscarPorCursoEAno(@Param("curso") String curso, @Param("ano") int ano, Pageable pageable);
}
