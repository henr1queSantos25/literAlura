package com.henr1que.literalura;

import com.henr1que.literalura.principal.Principal;
import com.henr1que.literalura.repository.AutorRepository;
import com.henr1que.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    @Autowired
    LivroRepository repositorio;

    @Autowired
    AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repositorio, autorRepository);
        principal.exibeMenu();
    }
}
