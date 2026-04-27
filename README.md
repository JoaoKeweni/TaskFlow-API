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
- [x] Configuração inicial do projeto (Spring Initializr)
- [x] Configuração da conexão com banco de dados PostgreSQL
- [ ] Modelagem de Entidades e Relacionamentos
- [ ] Criação dos Repositories
- [ ] Implementação da Camada de Service e Business Logic
- [ ] Exposição de Endpoints (Controllers)
- [ ] Implementação de DTOs e Mappers
- [ ] Tratamento Global de Exceções
- [ ] Segurança com Spring Security e JWT
- [ ] Documentação Interativa com Swagger
- [ ] Containerização com Docker

## Como rodar o projeto localmente
1. Clone o repositório
2. Configure as credenciais do PostgreSQL no arquivo `application.properties`
3. Execute `./mvnw spring-boot:run` dentro da pasta `api`
