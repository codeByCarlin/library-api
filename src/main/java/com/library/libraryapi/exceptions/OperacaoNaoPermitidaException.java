package com.library.libraryapi.exceptions;

import lombok.Getter;

public class OperacaoNaoPermitidaException extends RuntimeException {
    @Getter
    private String campo;

    public OperacaoNaoPermitidaException(String message) {
        super(message);
    }

    public OperacaoNaoPermitidaException(String message, String campo) {
        super(message);
        this.campo = campo;
    }
}
