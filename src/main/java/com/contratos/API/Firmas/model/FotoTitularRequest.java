package com.contratos.API.Firmas.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Cuerpo del request que envia la app Android (Retrofit).
 * La imagen viaja como cadena Base64 (sin el prefijo "data:image/...;base64,").
 */
public class FotoTitularRequest {

    @NotNull(message = "IdTitular es obligatorio")
    private Integer idTitular;

    @NotBlank(message = "imagenBase64 es obligatorio")
    private String imagenBase64;

    private String extension = "png";
    private String nombreCompleto;;

    public Integer getIdTitular() {
        return idTitular;
    }

    public void setIdTitular(Integer idTitular) {
        this.idTitular = idTitular;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
