# TaskFlow API

![Status](https://img.shields.io/badge/Status-Finalizado-success?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?style=for-the-badge&logo=postgresql)

Uma API REST completa para gerenciamento de tarefas inspirada no Trello, desenvolvida com Java e Spring Boot. O objetivo desta API é fornecer toda a estrutura de *backend* necessária para a criação de um quadro "Kanban" funcional e robusto.

## Resumo do Projeto

O **TaskFlow API** permite o gerenciamento completo de:
- **Usuários:** Criação de contas de usuário.
- **Quadros (Boards):** Cada usuário pode ter vários quadros.
- **Colunas (Board Columns):** Organização das etapas (Ex: A Fazer, Em Progresso, Concluído).
- **Tarefas (Tasks):** Cartões de tarefas que pertencem a uma coluna e a um usuário, contendo título, descrição e prioridade.

A API foi projetada com foco em **boas práticas**, utilizando DTOs (Data Transfer Objects) para entrada e saída de dados.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.x** (Web, Data JPA, Validation)
- **PostgreSQL** (Banco de dados relacional)
- **Lombok** (Redução de código / Boilerplate)
- **Maven** (Gerenciamento de dependências)

## 📌 Arquitetura e Implementações de Destaque

- **CORS Configurado:** Pronta para consumo por qualquer interface de Frontend moderna (ex: React, Vite, Next.js).
- **Relacionamentos JPA Dinâmicos:** Relacionamentos bidirecionais `@OneToMany` e `@ManyToOne`. Ao buscar a lista de Quadros, a API processa e entrega de forma organizada a hierarquia completa: **Board -> Columns -> Tasks**, facilitando a montagem visual do quadro.
- **Bean Validation:** Validação rigorosa nos dados de entrada (`@NotBlank`, `@Email`, `@NotNull`), barrando tentativas de inserir dados inconsistentes.
- **Global Exception Handler:** Interceptador de erros que transforma `Exceptions` e falhas de validação em respostas JSON limpas, organizadas e amigáveis, com seus devidos HTTP Status (ex: 400 Bad Request).

## Como rodar o projeto localmente

### 1. Pré-requisitos
- [Java 21+](https://jdk.java.net/21/) instalado.
- Banco de dados [PostgreSQL](https://www.postgresql.org/) rodando na porta `5432`.

### 2. Configurando o Banco de Dados
Certifique-se de que o seu banco de dados local corresponde as credenciais no arquivo `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskflow
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

### 3. Executando a API
Abra o terminal na pasta `/api` e execute o comando:
```bash
./mvnw spring-boot:run
```
A API estará disponível e escutando requisições em `http://localhost:8080`.

##  Endpoints Principais

A API responde na rota base `/api`.

### Usuários (`/api/users`)
- `POST /` - Cria um novo usuário.

### Quadros (`/api/boards`)
- `GET /` - Lista todos os quadros (Trazendo junto suas colunas e as tarefas de cada coluna).
- `POST /` - Cria um novo quadro.

### Colunas (`/api/columns`)
- `POST /` - Cria uma nova coluna vinculada a um quadro.

### Tarefas (`/api/tasks`)
- `GET /` - Lista todas as tarefas.
- `GET /{id}` - Busca os detalhes de uma tarefa específica.
- `POST /` - Cria uma nova tarefa dentro de uma coluna.
- `PUT /{id}` - Atualiza dados (como título ou mover a tarefa para outra coluna).
- `DELETE /{id}` - Exclui permanentemente uma tarefa.

---
*Status: Finalizado - Desenvolvido para facilitar integrações.*
