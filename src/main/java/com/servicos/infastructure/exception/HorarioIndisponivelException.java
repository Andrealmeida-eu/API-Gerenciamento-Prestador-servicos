package com.servicos.infastructure.exception;

public class HorarioIndisponivelException extends RuntimeException {
    public HorarioIndisponivelException(String message) {
        super(message);
    }
}
