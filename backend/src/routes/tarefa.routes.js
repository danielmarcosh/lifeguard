const router = require("express-promise-router")();
const { verifyJWT } = require("../middleware/authentication");
const tarefaController = require("../controllers/tarefa.controller");

router.post("/tarefa", tarefaController.criarTarefa);
router.get("/tarefas", tarefaController.listarTarefas);
router.post("/editar/tarefa", tarefaController.editarTarefas);
router.delete("/deletar/:id", tarefaController.deletarTarefa);

// router.post("/tarefa", verifyJWT, tarefaController.criarTarefa);
// router.get("/tarefas", verifyJWT, tarefaController.listarTarefas);
// router.post("/editar/tarefa", verifyJWT, tarefaController.editarTarefas);
// router.delete("/deletar/:id", verifyJWT, tarefaController.deletarTarefa);

module.exports = router;
