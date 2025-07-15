package br.com.mpb.forumhub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String senha;

    @OneToMany(mappedBy = "autor")
    private List<Topico> topicos;
    @OneToMany(mappedBy = "autor")
    private List<Resposta> respostas;

    public Usuario(String nome, String email, String senhaCriptografada) {
        this.nome = nome;
        this.email = email;
        this.senha = senhaCriptografada;
    }
}
