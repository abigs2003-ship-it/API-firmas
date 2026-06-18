const Joi = require("joi");

module.exports = Joi.object({
    idTitular: Joi.number().integer().required(),
    nombreCompleto: Joi.string().required(),
    imagenBase64: Joi.string().required(),
    extension: Joi.string()
        .valid("jpg", "jpeg", "png")
        .default("png")
});