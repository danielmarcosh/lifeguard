const { db } = require("../config/database");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

exports.criarUsuario = async (req, res) => {
  const { nome, email, senha } = req.body;
  console.log("REQUEST: ", req.body);
  const senha_bcrypt = bcrypt.hashSync(senha, 10);
  const { rows } = await db.query(
    "INSERT INTO usuario (nome, email, senha) VALUES ($1, $2, $3)",
    [nome, email, senha_bcrypt]
  );
  console.log(rows);

  res.status(201).send({
    message: `${nome}. Usuario criado com sucesso!`,
    body: {
      usuario: { nome, email },
    },
  });
};

async function authenticate(email, senha) {
  const { rows } = await db.query("SELECT * FROM usuario WHERE email = $1", [
    email,
  ]);
  const senhaHash = rows[0].senha;

  if (!rows || !bcrypt.compareSync(senha, senhaHash)) {
    return false;
  } else {
    const usuario = {
      id: rows[0].id,
      nome: rows[0].nome,
      email: rows[0].email,
    };
    return usuario;
  }
}

exports.listarUsuarios = async (req, res) => {
  const { rows } = await db.query("SELECT nome, email FROM usuario");
  console.log("Usuarios: ", rows);

  res.status(200).send({
    message: `Sucesso!`,
    body: {
      usuarios: rows,
    },
  });
};
exports.login = async (req, res, next) => {
  const { email, senha } = req.body;
  const result = await authenticate(email, senha);

  if (result) {
    const id = result.id;
    const token = jwt.sign({ id }, process.env.SECRET, {
      expiresIn: 7200000,
    });

    return res.json({ auth: true, token: token });
  }

  res.status(500).json({ message: "Login inválido!" });
};
exports.logout = async (req, res, next) => {
  res.json({ auth: false, token: null });
};
