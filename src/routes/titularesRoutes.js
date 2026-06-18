const express = require('express');
const router = express.Router();
const { subirFoto } = require('../controllers/fotoTitularController');

router.post('/foto', subirFoto);

module.exports = router;
