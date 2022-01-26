CREATE TABLE Usuario (
  id serial not null primary key,
  nome varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  senha varchar(255) NOT NULL
);

CREATE TABLE Tarefa (
  id serial not null primary key,
  nome varchar(255) NOT NULL,
  descricao text NOT NULL,
  data date NOT NULL,
  id_usuario int not null,
  FOREIGN KEY (id_usuario) REFERENCES Usuario(id)
);