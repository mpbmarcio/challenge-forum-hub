package br.com.mpb.forumhub.model;

import br.com.mpb.forumhub.dto.request.AtualizarRequestStatusDTO;
import br.com.mpb.forumhub.dto.request.TopicoRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "topicos")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataInc;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Resposta> respostas = new ArrayList<>();


    public Topico(String titulo, String mensagem, LocalDateTime dataInc, Status status, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataInc = dataInc;
        this.status = status;
        this.autor = autor;
        this.curso = curso;
    }

    public void atualizar(TopicoRequestDTO dados) {
        if (dados.titulo() != null) {
            this.titulo = dados.titulo();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
    }

    public void atualizarStatus(AtualizarRequestStatusDTO dados) {
        if (dados.status() != null) {
            this.status = dados.status();
        }
    }
}
