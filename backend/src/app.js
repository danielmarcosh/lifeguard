const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");

const app = express();

const usuarioRoutes = require("./routes/usuario.routes");
const tarefaRoutes = require("./routes/tarefa.routes");

app.use(bodyParser.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.use(express.json({ type: "application/vnd.api+json" }));
app.use(cors());

app.use("/api/", usuarioRoutes);
app.use("/api/", tarefaRoutes);

module.exports = app;
