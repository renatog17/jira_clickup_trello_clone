
# AgileFlow

AgileFlow é uma aplicação de gerenciamento de projetos inspirada em ferramentas como Trello, ClickUp e Jira. Esta aplicação permite a criação de quadros Kanban, gestão de tarefas e projetos, com funcionalidades avançadas para organizar e monitorar o progresso de equipes.

## Funcionalidades

- **Gestão de Projetos:** Criação de múltiplos projetos, cada um com seu próprio quadro (board) Kanban.
- **Tarefas:** Criação, edição, arquivamento e gestão de tarefas.
- **Quadros Kanban:** Organize suas tarefas em diferentes colunas para visualizar o progresso do projeto.
- **Registro de Atividades:** Histórico de ações realizadas nos projetos e tarefas.
- **Atribuição de Usuários:** Atribua membros da equipe a projetos e tarefas.
- **Integração com Banco de Dados:** Utiliza Spring Data JPA para persistência de dados.
- **Testes de Integração:** Testes configurados para garantir a qualidade do código.

## Tecnologias Utilizadas

- **Java 8** e **Java 17**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **H2 Database (para testes)**
- **PostgreSQL** (ou outro banco de dados relacional)
- **Maven** para gerenciamento de dependências

## Instalação

1. Clone o repositório:

   ```bash
   git clone https://github.com/seu-usuario/agileflow.git
   cd agileflow
   ```

2. Configure o banco de dados no arquivo `application.properties`.

3. Execute a aplicação:

   ```bash
   mvn spring-boot:run
   ```

## Como Contribuir

1. Faça um fork do projeto.
2. Crie um branch para a sua funcionalidade: `git checkout -b minha-funcionalidade`.
3. Faça commit das suas alterações: `git commit -m 'Minha nova funcionalidade'`.
4. Envie seu branch: `git push origin minha-funcionalidade`.
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a licença MIT. Para mais detalhes, veja o arquivo [LICENSE](LICENSE).
