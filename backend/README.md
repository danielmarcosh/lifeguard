# Backend Lifeguard

## Pré-requisto

- Node version +12.13.0
- PostgreSQL

Crie um banco de dados, dentro do projeto backend, o arquivo ".sql" possui os comandos
SQL para criar as tabelas necessárias. Depois crie um arquivo ".env" no
diretorio raiz do projeto "banckend" e insira as suas credenciais do Banco de dados no arquivo .env
O arquivo .env deve conter estas informações no seguinte formato:
DATABASE_URL=postgres://username:password@host:port/database
SECRET=meusegredo

### Para levantar o Servidor
1- rode o comando "npm install" no diretório raiz do projeto "backend"
2- rode o comando "npx nodemon server.js"


### Rotas

POST. http://localhost:4000/api/usuario         (criar usuario)
GET. http://localhost:4000/api/usuarios         (listar usuarios)

POST. http://localhost:4000/api/login           (fazer login)
POST. http://localhost:4000/api/logout          (fazer logout)

POST. http://localhost:4000/api/tarefa          (criar tarefa)
GET. http://localhost:4000/api/tarefas          (listar tarefas)
POST. http://localhost:4000/api/editar/tarefa   (editar tarefa)
DELETE. http://localhost:4000/api/deletar/:id   (deletar tarefa)
