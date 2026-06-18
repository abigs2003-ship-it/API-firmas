const service =
    require("../services/fotoTitularService");

const schema =
    require("../models/fotoTitularRequest");

exports.guardarFoto =
    async (req, res, next) => {

        try {

            const { error, value } =
                schema.validate(req.body);

            if (error) {

                return res.status(400).json({
                    exito: false,
                    mensaje: error.details[0].message
                });
            }

            const ruta =
                await service.guardarFotoYRegistrar(
                    value
                );

            res.json({
                exito: true,
                mensaje:
                    "Foto guardada y registrada correctamente",
                rutaFirma: ruta
            });

        } catch (err) {
            next(err);
        }
    };

exports.ping =
    (req, res) => {

        res.json({
            exito: true,
            mensaje: "Servidor activo",
            rutaFirma: null
        });
    };

exports.testRequest =
    (req, res) => {

        const body = req.body;

        res.json({
            idTitular: body.idTitular,
            nombreCompleto:
                body.nombreCompleto,
            extension:
                body.extension,
            imagenBase64_longitud:
                body.imagenBase64
                    ? body.imagenBase64.length
                    : 0,
            imagenBase64_primeros50chars:
                body.imagenBase64
                    ? body.imagenBase64.substring(
                        0,
                        50
                    )
                    : null
        });
    };
    //hola mundo