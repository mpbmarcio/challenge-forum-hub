package br.com.mpb.forumhub.repository;

import br.com.mpb.forumhub.model.Resposta;
import br.com.mpb.forumhub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    List<Resposta> findByTopico(Topico topico);
}
