package com.empresa.firmatitular.model;

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

    // Opcional: jpg, png. Si no se manda, se asume jpg.
    private String extension = "jpg";

    public Integer getIdTitular() {
        return idTitular;
    }

    public void setIdTitular(Integer idTitular) {
        this.idTitular = idTitular;
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
