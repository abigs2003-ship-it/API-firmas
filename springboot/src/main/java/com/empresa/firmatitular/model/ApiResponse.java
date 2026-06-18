package com.empresa.firmatitular.model;

public class ApiResponse {

    private boolean exito;
    private String mensaje;
    private String rutaFirma;

    public ApiResponse() {
    }

    public ApiResponse(boolean exito, String mensaje, String rutaFirma) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.rutaFirma = rutaFirma;
    }

    public static ApiResponse ok(String mensaje, String rutaFirma) {
        return new ApiResponse(true, mensaje, rutaFirma);
    }

    public static ApiResponse error(String mensaje) {
        return new ApiResponse(false, mensaje, null);
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRutaFirma() {
        return rutaFirma;
    }

    public void setRutaFirma(String rutaFirma) {
        this.rutaFirma = rutaFirma;
    }
}
