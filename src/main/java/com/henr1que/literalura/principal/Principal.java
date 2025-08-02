package com.henr1que.literalura.principal;

import com.henr1que.literalura.model.Autor;
import com.henr1que.literalura.model.Dados;
import com.henr1que.literalura.model.Livro;
import com.henr1que.literalura.repository.AutorRepository;
import com.henr1que.literalura.repository.LivroRepository;
import com.henr1que.literalura.service.ConsumoAPI;
import com.henr1que.literalura.service.ConverteDados;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoAPI consumo = new ConsumoAPI();
    private final ConverteDados conversor = new ConverteDados();
    private final LivroRepository repositorioLivro;
    private final AutorRepository repositorioAutor;

    private final String ENDERECO_API = "https://gutendex.com/books/?search=";

    public Principal(LivroRepository repositorioLivro, AutorRepository repositorioAutor) {
        this.repositorioLivro = repositorioLivro;
        this.repositorioAutor = repositorioAutor;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livro por título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair                                \s
                   \s""";

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do scanner

            switch (opcao) {
                case 1 -> {
                    buscarLivroPorTitulo();
                }
                case 2 -> {
                    listarLivrosRegistrados();
                }
                case 3 -> {
                    listarAutoresResgistrados();
                }
                case 4 -> {
                    listarAutoresVivos();
                }
                case 5 -> {
                    listarLivrosPorIdioma();
                }
            }
        }
    }

    private void imprimeAutores(List<Autor> autoresVivos) {
        for(Autor a : autoresVivos) {
            System.out.println("Nome: " + a.getNome());
            System.out.println("Data de Nascimento: " + a.getAnoNascimento());
            System.out.println("Data da Morte: " + a.getAnoFalecimento());
            System.out.println("Livros:");
            a.getLivros().forEach(l -> System.out.println(l.getTitulo()));

            // System.out.println("Livros: " + autor.getLivros());
            System.out.println("----------------------------");
        }
    }

    private void imprimeLivros(List<Livro> livrosPorIdioma) {
        for (Livro livro : livrosPorIdioma) {
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + livro.getAutor().getNome());
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Número de downloads: " + livro.getNumeroDownloads());
            System.out.println("----------------------------");
        }
    }

    private void buscarLivroPorTitulo() {
        Dados dados = getDadosLivro();

        // Verifica se o livro já existe no repositório
        Autor autor = repositorioAutor.findByNome(dados.livros().getFirst().autores().get(0).nome())
                .orElseGet(() -> {
                    Autor novoAutor = new Autor(dados.livros().getFirst().autores().get(0));
                    return repositorioAutor.save(novoAutor);
                });

        Optional<Livro> livroVerificado = repositorioLivro.findByTitulo(dados.livros().getFirst().titulo());

        if (livroVerificado.isPresent()) {
            System.out.println("O livro " + livroVerificado.get().getTitulo() + " já está cadastrado!");
            return;
        } else {
            // Cria um novo livro com os dados obtidos
            Livro livro = new Livro(dados.livros().getFirst());
            livro.setAutor(autor);
            repositorioLivro.save(livro);

            System.out.println("\n----------------------------");
            System.out.println("Livro adicionado com sucesso!");
            System.out.println("Título: " + livro.getTitulo());
            System.out.println("Autor: " + livro.getAutor().getNome());
            System.out.println("Idioma: " + livro.getIdioma());
            System.out.println("Número de downloads: " + livro.getNumeroDownloads());
            System.out.println("----------------------------\n");
        }
    }

    private Dados getDadosLivro() {
        System.out.println("Digite o título do livro:");
        var titulo = scanner.nextLine();
        var json = consumo.obterDados(ENDERECO_API + titulo.replace(" ", "+").toLowerCase());
        return conversor.converterDados(json, Dados.class);
    }

    private void listarLivrosRegistrados() {
        var livros = repositorioLivro.findAll();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
        } else {
            System.out.println("\nLista de livros registrados:");
            System.out.println("----------------------------");
            imprimeLivros(livros);
        }
    }

    private void listarAutoresResgistrados() {
        var autores = repositorioAutor.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            System.out.println("\nLista de autores registrados:");
            System.out.println("----------------------------");
            imprimeAutores(autores);
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Insira o ano que deseja pesquisar");
        var ano = scanner.nextInt();
        List<Autor> autoresVivos = repositorioAutor.findAutoresVivos(ano);

        if(autoresVivos.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
        } else {
            System.out.println("\nLista de autores registrados:");
            imprimeAutores(autoresVivos);
        }
    }

    private void listarLivrosPorIdioma() {
        var menu = """
                    es - espanhol
                    en - inglês
                    fr - francês
                    pt - português
                    """;

        System.out.println(menu);
        System.out.println("Escolha o idioma para realizar a busca: ");

        var idioma = scanner.nextLine();

        List<Livro> livrosPorIdioma = repositorioLivro.findByIdioma(idioma);

        if(livrosPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            System.out.println("\nLista de livros encontrados:");
            imprimeLivros(livrosPorIdioma);
        }
    }
}
