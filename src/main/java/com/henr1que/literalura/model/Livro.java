package com.henr1que.literalura.model;


import java.util.ArrayList;
import java.util.List;
import com.henr1que.literalura.model.Dados.DadosLivro;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    String idioma;
    private Integer numeroDownloads;

    @ManyToOne
    Autor autor = new Autor();

    public Livro(DadosLivro dados) {
        this.titulo = dados.titulo();
        this.idioma = dados.idiomas().getFirst();
        this.numeroDownloads = dados.numeroDownloads();
    }

    public Livro() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
