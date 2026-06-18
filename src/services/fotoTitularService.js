const fs = require("fs/promises");
const path = require("path");

const { sql, poolPromise } = require("../db/sqlServer");

class FotoTitularService {

    async guardarFotoYRegistrar(request) {

        const buffer = this.decodificarBase64(
            request.imagenBase64
        );

        const rutaArchivo =
            await this.guardarArchivoEnCarpeta(
                request.nombreCompleto,
                buffer,
                request.extension
            );

        await this.actualizarRutaEnBD(
            request.idTitular,
            rutaArchivo
        );

        return rutaArchivo;
    }

    decodificarBase64(base64) {

        try {

            const limpio = base64.includes(",")
                ? base64.substring(base64.indexOf(",") + 1)
                : base64;

            return Buffer.from(limpio, "base64");

        } catch {

            throw new Error(
                "La cadena Base64 de la imagen es invalida"
            );
        }
    }

    async guardarArchivoEnCarpeta(
        nombreCompleto,
        buffer,
        extension
    ) {

        const carpeta =
            process.env.RUTA_COMPARTIDA;

        await fs.mkdir(carpeta, {
            recursive: true
        });

        const nombreArchivo =
            `${nombreCompleto}.${extension}`;

        const rutaFinal =
            path.join(carpeta, nombreArchivo);

        await fs.writeFile(
            rutaFinal,
            buffer
        );

        console.log(
            `Imagen guardada en ${rutaFinal}`
        );

        return rutaFinal;
    }

    async actualizarRutaEnBD(
        idTitular,
        rutaFirma
    ) {

        const pool =
            await poolPromise;

        const result =
            await pool.request()
                .input(
                    "idTitular",
                    sql.Int,
                    idTitular
                )
                .input(
                    "rutaFirma",
                    sql.NVarChar,
                    rutaFirma
                )
                .query(`
                    EXEC SP_ActualizarRutaFirmaTitular
                    @idTitular,
                    @rutaFirma
                `);

        if (
            !result.recordset ||
            result.recordset.length === 0
        ) {
            throw new Error(
                "El SP no devolvio resultado"
            );
        }

        const row =
            result.recordset[0];

        if (row.Resultado !== 0) {

            throw new Error(
                `Error del SP: ${row.Mensaje}`
            );
        }
    }
}

module.exports =
    new FotoTitularService();