# Library API

API REST desenvolvida em **Java** e **Spring Boot** (com Maven) para gerenciamento de **Autores** e **Livros**, estruturada em camadas com separação de responsabilidades, DTOs, mappers, validações, tratamento de exceções, paginação, integração inicial com **Spring Security** e uso simples de **Docker** para gerenciar o container (sem Docker Compose ou orquestração).

## Funcionalidades e arquitetura

- **Camada de controle (`controller`, `dto`, `mapper`, `common`)**: endpoints REST (`AutorController`, `LivroController`, `GenericController`), objetos de requisição/resposta (`AutorDTO`, `AutorResponseDTO`, `LivroDTO`, `LivroResponseDTO`), padronização de erros (`ErroResponse`, `ErroCampo`) e mapeamento entre entidades e DTOs.
- **Regras de negócio e validação (`service`, `validator`, `exceptions`)**: serviços (`AutorService`, `LivroService`), validadores dedicados (`AutorValidator`, `LivroValidator`) e exceções customizadas (`OperacaoNaoPermitidaException`, `RegistroCadastradoException`).
- **Persistência, filtros e paginação (`model`, `repository`, `repository/specs`)**: entidades e enums (`Autor`, `Livro`, `Genero`), repositórios (`AutorRepository`, `LivroRepository`), filtros dinâmicos com Specifications e listagem paginada.
- **Infraestrutura com Docker**: uso direto de comandos básicos do Docker (download de imagem e controle de ciclo de vida do container), sem Docker Compose ou orquestração.
- **Testes (`src/test/java`)**: testes automatizados (`AutorRepositoryTest`, `LivroRepositoryTest`, `LibraryapiApplicationTests`).

## Tecnologias

- Java
- Spring Boot
- Spring Web / Spring Data JPA / Spring Security (conforme dependências do `pom.xml`)
- Docker (uso simples de container, sem Compose)
- Maven (`mvnw` / `pom.xml`)

## Como executar localmente

### 1. Subir o container com Docker (se aplicável)
Faça o download/criação do container com a imagem desejada (ex.: banco de dados) e controle o ciclo de vida diretamente pelo nome do container:

```bash
docker pull <imagem>
docker run --name <nome-do-container> -d <imagem>

# Para iniciar ou parar o container depois:
docker start <nome-do-container>
docker stop <nome-do-container>