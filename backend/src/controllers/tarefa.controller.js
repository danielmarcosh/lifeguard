const { db } = require("../config/database");

exports.criarTarefa = async (req, res) => {
  const { nome, descricao, data, id_usuario } = req.body;

  console.log("REQUEST: ", req.body);

  const { rows } = await db.query(
    "INSERT INTO tarefa (nome, descricao, data, id_usuario) VALUES ($1, $2, $3, $4)",
    [nome, descricao, data, id_usuario]
  );

  return res.status(201).send({
    message: `${nome}. Tarefa criada com sucesso!`,
  });
};

exports.listarTarefas = async (req, res) => {
  const { id_usuario } = req.body;
  const { rows } = await db.query(
    "SELECT * FROM tarefa WHERE id_usuario = $1",
    [id_usuario]
  );
  console.log(JSON.stringify(rows));

  return res.status(200).send({
    message: `Sucesso!`,
    body: {
      tarefas: JSON.stringify(rows),
    },
  });
};
exports.editarTarefas = async (req, res) => {
  const { id, nome, descricao, data } = req.body;

  console.log("Tarefa: ", nome);

  const { rows } = await db.query(
    "UPDATE tarefa SET nome = $1, descricao = $2, data = $3 WHERE id = $4",
    [nome, descricao, data, id]
  );

  return res.status(200).send({
    message: `${nome}. Tarefa editada com sucesso!`,
  });
};
exports.deletarTarefa = async (req, res) => {
  let id = req.params.id;

  const { rows } = await db.query("DELETE FROM tarefa WHERE id = $1", [id]);

  return res.status(201).send({
    message: `Tarefa deletada com sucesso!`,
  });
};
