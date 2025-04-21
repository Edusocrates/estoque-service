package com.fiap.rm358568.edusocrates.estoque_service.API.exceptions;

public class EstoqueIndisponivelException extends RuntimeException {
    public EstoqueIndisponivelException(String message) {
        super(message);
    }
}
