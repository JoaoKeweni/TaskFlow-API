# TaskFlow API

Uma API REST completa para gerenciamento de tarefas inspirada no Trello, desenvolvida com Java e Spring Boot.

## Tecnologias Utilizadas
- Java 21
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL
- Lombok
- Docker & Docker Compose
- Swagger (OpenAPI)
- JWT (JSON Web Token)

## Roadmap de Desenvolvimento
- [x] **Configuração inicial do projeto**: Setup via Spring Initializr.
- [x] **Conexão com Banco de Dados**: Configuração do PostgreSQL no `application.properties`.
- [x] **Modelagem de Entidades**: Criação das classes `User`, `Board`, `BoardColumn` e `Task` com seus relacionamentos.
- [x] **Camada de Persistência**: Criação dos `Repositories` com Spring Data JPA.
- [x] **Camada de Serviço**: Implementação das regras de negócio.
- [x] **DTOs e Mappers**: Separação entre modelos de banco e modelos de API.
- [x] **Controllers (API REST)**: Criação dos endpoints para os CRUDs.
- [ ] **Tratamento Global de Exceções**: Padronização de erros da API.
- [ ] **Segurança**: Implementação de Spring Security e JWT.
- [ ] **Documentação Interativa**: Configuração do Swagger/OpenAPI.
- [ ] **Dockerização**: Criação de `Dockerfile` e `docker-compose.yml`.

## Como rodar o projeto localmente
1. Clone o repositório
2. Configure as credenciais do PostgreSQL no arquivo `application.properties`
3. Execute `./mvnw spring-boot:run` dentro da pasta `api`
