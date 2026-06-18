const express = require("express");

const router =
    express.Router();

const controller =
    require("../controllers/fotoTitularController");

router.post(
    "/foto",
    controller.guardarFoto
);

router.get(
    "/ping",
    controller.ping
);

router.post(
    "/test",
    controller.testRequest
);

module.exports = router;