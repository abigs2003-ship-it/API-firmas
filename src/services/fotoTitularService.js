const { sql, executeProcedure } = require('../db/sqlServer');

async function actualizarRutaFirma({ idTitular, rutaFirma }) {
    const params = {
        IdTitular: { type: sql.Int, value: idTitular },
        RutaFirma: { type: sql.VarChar(500), value: rutaFirma },
    };

    const result = await executeProcedure('SP_ActualizarRutaFirmaTitular', params);
    return result;
}

module.exports = { actualizarRutaFirma };
