const router = require("express-promise-router")();
const usuarioController = require("../controllers/usuario.controller");

router.post("/usuario", usuarioController.criarUsuario);
router.get("/usuarios", usuarioController.listarUsuarios);

router.post("/login", usuarioController.login);
router.post("/logout", usuarioController.logout);

module.exports = router;
