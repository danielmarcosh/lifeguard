const { db } = require("../config/database");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

exports.criarUsuario = async (req, res) => {
  const { nome, email, senha } = req.body;
  console.log("REQUEST: ", req.body);
  const senha_bcrypt = bcrypt.hashSync(senha, 10);
  const { rows } = await db.query(
    "INSERT INTO usuario (nome, email, senha) VALUES ($1, $2, $3) RETURNING id",
    [nome, email, senha_bcrypt]
  );
  console.log(rows);

  return res.status(201).json({
    id: rows[0].id,
  });
};

async function authenticate(email, senha) {
  const { rows } = await db.query("SELECT * FROM usuario WHERE email = $1", [
    email,
  ]);
  console.log("RESULTADO: ", rows);

  if (rows.length === 0) {
    console.log("Usuario nao encontrado");
    return false;
  }
  if (bcrypt.compareSync(senha, rows[0].senha)) {
    console.log("Login valido");
    const usuario = {
      id: rows[0].id,
      nome: rows[0].nome,
      email: rows[0].email,
    };
    return usuario;
  } else {
    console.log("Login invalido");
    return false;
  }
}

exports.listarUsuarios = async (req, res) => {
  const { rows } = await db.query("SELECT nome, email FROM usuario");
  console.log("Usuarios: ", rows);

  return res.status(200).json({
    message: `Sucesso!`,
    usuarios: rows,
  });
};
exports.login = async (req, res, next) => {
  console.log("REQUEST: ", req.body);
  const { email, senha } = req.body;
  const result = await authenticate(email, senha);

  if (result) {
    const id = result.id;
    const token = jwt.sign({ id }, process.env.SECRET, {
      expiresIn: 7200000,
    });

    const resposta = { id, ...result, auth: true, token: token };

    return res.status(200).json(resposta);
  }

  return res.status(400).json({ message: "Login inválido!" });
};

// exports.logout = async (req, res, next) => {
//   res.json({ auth: false, token: null });
// };
