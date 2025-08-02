package com.henr1que.literalura.repository;

import com.henr1que.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNome(String nome);

    @Query(value = "SELECT a FROM Autor a WHERE :ano BETWEEN a.anoNascimento AND a.anoFalecimento")
    List<Autor> findAutoresVivos(int ano);
}
