package com.henr1que.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(@JsonAlias("count") Integer count,
                    @JsonAlias("results") List<DadosLivro> livros){


    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DadosLivro(@JsonAlias("title") String titulo,
                             @JsonAlias("authors") List<DadosAutor> autores,
                             @JsonAlias("languages") List<String> idiomas,
                             @JsonAlias("download_count") Integer numeroDownloads) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DadosAutor(@JsonAlias("name") String nome,
                      @JsonAlias("birth_year") Integer anoNascimento,
                      @JsonAlias("death_year") Integer anoFalecimento) {
    }


}

