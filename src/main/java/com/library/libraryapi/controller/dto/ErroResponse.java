package com.library.libraryapi.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResponse(String mensagem, int status, List<ErroCampo> erros) {

    public static ErroResponse respostaPadrao(String mensagem) {
        return new ErroResponse(mensagem, HttpStatus.BAD_REQUEST.value(), List.of());
    }

    public static ErroResponse conflito(String mensagem) {
        return new ErroResponse(mensagem, HttpStatus.CONFLICT.value(), List.of());
    }
}
