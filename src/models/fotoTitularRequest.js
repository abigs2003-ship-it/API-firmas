// Modelo de validación simple para la petición de foto titular
class FotoTitularRequest {
    constructor({ idTitular, rutaFirma }) {
        this.idTitular = Number(idTitular);
        this.rutaFirma = rutaFirma;
    }

    isValid() {
        return Number.isInteger(this.idTitular) && this.idTitular > 0 && typeof this.rutaFirma === 'string' && this.rutaFirma.length > 0;
    }
}

module.exports = FotoTitularRequest;
