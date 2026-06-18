package com.contratos.API.Firmas.exception;

public class FirmaTitularException extends RuntimeException {

    public FirmaTitularException(String message) {
        super(message);
    }

    public FirmaTitularException(String message, Throwable cause) {
        super(message, cause);
    }
}
