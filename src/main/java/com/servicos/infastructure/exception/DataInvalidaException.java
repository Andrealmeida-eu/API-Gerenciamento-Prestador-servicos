package com.servicos.infastructure.exception;


public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException(String message) {
        super(message);
    }
}