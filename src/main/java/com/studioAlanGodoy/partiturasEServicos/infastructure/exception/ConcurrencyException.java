package com.studioAlanGodoy.partiturasEServicos.infastructure.exception;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(String message) {
        super(message);
    }
}
