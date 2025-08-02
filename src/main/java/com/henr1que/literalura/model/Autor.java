package com.henr1que.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import com.henr1que.literalura.model.Dados.DadosAutor;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;


    @OneToMany (mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome();
        this.anoNascimento = dadosAutor.anoNascimento();
        this.anoFalecimento = dadosAutor.anoFalecimento();
    }

    public Autor() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
}
