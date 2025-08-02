# LiterAlura: Catálogo de Livros Digitais

O **LiterAlura** é uma aplicação Java desenvolvida com **Spring Boot** que funciona como um catálogo interativo de livros digitais. O sistema integra com a API **Gutendex** para buscar informações sobre livros do domínio público, permitindo aos usuários descobrir, catalogar e gerenciar uma biblioteca pessoal com dados detalhados sobre obras literárias e seus autores.

---

## Funcionalidades Principais

- **Busca Inteligente**: Consulta de livros por título através da API Gutendex com persistência local.
- **Catálogo Pessoal**: Armazenamento de livros e autores em banco de dados PostgreSQL.
- **Gestão de Autores**: Listagem completa de autores com suas respectivas obras.
- **Filtro Temporal**: Consulta de autores que estavam vivos em um ano específico.
- **Busca por Idioma**: Filtragem de livros por idioma (português, inglês, francês, espanhol).
- **Informações Detalhadas**: Exibição completa de dados incluindo:
  - Título e autor da obra
  - Idioma original
  - Número de downloads na plataforma
  - Dados biográficos dos autores (nascimento/falecimento)
- **Interface Intuitiva**: Menu interativo via linha de comando com opções numeradas.
- **Prevenção de Duplicatas**: Verificação automática antes de adicionar livros já catalogados.

---

## Tecnologias Utilizadas

- **Linguagem**: Java 21
- **Framework**: Spring Boot 3.5.4
- **Gerenciamento de Dependências**: Maven
- **Banco de Dados**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Consumo de API**: HttpClient nativo do Java (java.net.http)
- **Processamento JSON**: Jackson Databind 2.17.0
- **Arquitetura**: MVC com separação de camadas (Model, Repository, Service, Principal)

---

## API Utilizada

### Gutendex API
- **URL Base**: `https://gutendex.com/books/`
- **Endpoint principal**: `?search={titulo}` - Busca livros por título
- **Documentação**: [https://gutendex.com](https://gutendx.com)

### Formato de Resposta
```json
{
  "count": 1,
  "results": [
    {
      "title": "Dom Casmurro",
      "authors": [
        {
          "name": "Machado de Assis",
          "birth_year": 1839,
          "death_year": 1908
        }
      ],
      "languages": ["pt"],
      "download_count": 5420
    }
  ]
}
```

---

## Estrutura do Projeto

```
src/main/java/com/henr1que/literalura/
├── LiterAluraApplication.java          # Classe principal Spring Boot
├── model/
│   ├── Dados.java                      # Records para mapeamento JSON
│   ├── Autor.java                      # Entidade JPA para autores
│   └── Livro.java                      # Entidade JPA para livros
├── principal/
│   └── Principal.java                  # Lógica principal e menu interativo
├── repository/
│   ├── AutorRepository.java            # Repository JPA para autores
│   └── LivroRepository.java            # Repository JPA para livros
└── service/
    ├── ConsumoAPI.java                 # Serviço para consumo da API
    ├── ConverteDados.java              # Implementação da conversão JSON
    └── IConverteDados.java             # Interface para conversão de dados
```

---

## Como Funciona

### Fluxo Principal
1. **Menu Interativo**: Usuário escolhe uma das 5 opções disponíveis
2. **Busca de Livros**: Consulta à API Gutendx por título
3. **Verificação de Duplicatas**: Checa se autor/livro já existe no banco
4. **Persistência**: Salva dados do autor e livro automaticamente
5. **Consultas Locais**: Operações de listagem e filtro no banco local
6. **Exibição Formatada**: Apresentação organizada dos resultados

### Menu de Opções
```
1 - Buscar livro por título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em um determinado ano
5 - Listar livros em um determinado idioma
0 - Sair
```

## Configuração e Execução

### Pré-requisitos
- Java 21 ou superior
- Maven 3.6+
- PostgreSQL instalado e configurado
- Conexão com internet para acessar a API

### Configuração do Banco de Dados
1. Crie um banco PostgreSQL chamado `literAlura`
2. Configure as variáveis de ambiente:
   ```bash
   export DB_HOST=localhost:5432
   export DB_USER=seu_usuario
   export DB_PASSWORD=sua_senha
   ```
---

## Modelo de Dados

### Entidade Autor
```java
@Entity
@Table(name = "autores")
public class Autor {
    @Id @GeneratedValue
    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;
    
    @OneToMany(mappedBy = "autor")
    private List<Livro> livros;
}
```

### Entidade Livro
```java
@Entity
@Table(name = "livros")
public class Livro {
    @Id @GeneratedValue
    private Long id;
    private String titulo;
    private String idioma;
    private Integer numeroDownloads;
    
    @ManyToOne
    private Autor autor;
}
```

### Relacionamentos
- **Um para Muitos**: Um autor pode ter vários livros
- **Muitos para Um**: Vários livros podem ter o mesmo autor
- **Cascade**: Operações em cascata para manter integridade

---

## Funcionalidades Detalhadas

### 1. Buscar Livro por Título
- Consulta à API Gutendx com o título fornecido
- Extração automática dos dados do primeiro resultado
- Verificação se o autor já existe no banco
- Criação de novo autor se necessário
- Persistência do livro com relacionamento ao autor

### 2. Listar Livros Registrados
- Consulta todos os livros salvos no banco local
- Exibição formatada com título, autor, idioma e downloads
- Mensagem informativa caso não existam livros

### 3. Listar Autores Registrados
- Consulta todos os autores com suas respectivas obras
- Exibição de dados biográficos completos
- Lista de livros associados a cada autor

### 4. Autores Vivos em Determinado Ano
- Query customizada usando JPQL
- Filtro por ano entre nascimento e falecimento
- Consulta: `WHERE :ano BETWEEN anoNascimento AND anoFalecimento`

### 5. Livros por Idioma
- Menu com códigos ISO 639-1 (pt, en, es, fr)
- Busca usando Spring Data JPA method query
- Filtro automático por idioma selecionado

---

## Arquitetura e Padrões

### Clean Architecture
- **Model**: Entidades JPA e Records para DTOs
- **Repository**: Camada de acesso a dados com Spring Data
- **Service**: Lógica de negócio e integração com APIs
- **Principal**: Camada de apresentação e controle de fluxo

### Padrões Utilizados
- **Repository Pattern**: Abstração do acesso a dados
- **Data Transfer Object**: Records para mapeamento JSON
- **Dependency Injection**: Injeção via Spring Boot
- **Builder Pattern**: Construção de entidades complexas
- **Strategy Pattern**: Interface para conversão de dados

### Recursos Spring Boot
- **Auto-configuration**: Configuração automática do JPA
- **CommandLineRunner**: Execução automática na inicialização
- **Environment Variables**: Configuração externa do banco
- **Query Methods**: Queries automáticas por convenção

---

## Tratamento de Erros

### Cenários Cobertos
- **Erro de Conexão**: Timeout ou falha na API Gutendx
- **Dados Não Encontrados**: Título inexistente na API
- **Erro de Banco**: Falha na conexão PostgreSQL
- **Parsing JSON**: Falha na conversão de dados da API
- **Entrada Inválida**: Validação de dados do usuário

### Exemplo de Tratamento
```java
try {
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
} catch (IOException | InterruptedException e) {
    throw new RuntimeException("Erro ao obter dados da API: " + e.getMessage(), e);
}
```

---

## Dependências Principais

| Dependência | Versão | Propósito |
|-------------|--------|-----------|
| Spring Boot Starter Data JPA | 3.5.4 | ORM e acesso a dados |
| PostgreSQL Driver | Runtime | Conectividade com PostgreSQL |
| Jackson Databind | 2.17.0 | Conversão JSON para objetos Java |
| Spring Boot Starter Test | 3.5.4 | Testes unitários e integração |

---

## Queries Customizadas

### Busca de Autores Vivos
```java
@Query("SELECT a FROM Autor a WHERE :ano BETWEEN a.anoNascimento AND a.anoFalecimento")
List<Autor> findAutoresVivos(int ano);
```

### Busca por Nome do Autor
```java
Optional<Autor> findByNome(String nome);
```

### Busca de Livros por Idioma
```java
List<Livro> findByIdioma(String idioma);
```

---

## Exemplos de Saída

### Adição de Livro
```
----------------------------
Livro adicionado com sucesso!
Título: O Cortiço
Autor: Aluísio Azevedo
Idioma: pt
Número de downloads: 3247
----------------------------
```

### Listagem de Autores
```
Lista de autores registrados:
----------------------------
Nome: Machado de Assis
Data de Nascimento: 1839
Data da Morte: 1908
Livros:
Dom Casmurro
O Alienista
----------------------------
Nome: José de Alencar
Data de Nascimento: 1829
Data da Morte: 1877
Livros:
O Guarani
Iracema
----------------------------
```

---

## Desenvolvido por

**Henrique Oliveira dos Santos**  
[LinkedIn](https://www.linkedin.com/in/dev-henriqueo-santos/)

---
