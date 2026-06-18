const { actualizarRutaFirma } = require('../services/fotoTitularService');

async function subirFoto(req, res, next) {
    try {
        const { idTitular, rutaFirma } = req.body;
        if (!idTitular || !rutaFirma) {
            return res.status(400).json({ message: 'idTitular y rutaFirma son requeridos' });
        }

        const result = await actualizarRutaFirma({ idTitular, rutaFirma });
        res.json({ success: true, result: result.recordset || result });
    } catch (err) {
        next(err);
    }
}

module.exports = { subirFoto };
