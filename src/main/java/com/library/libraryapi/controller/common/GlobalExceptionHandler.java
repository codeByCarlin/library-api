package com.library.libraryapi.controller.common;

import com.library.libraryapi.controller.dto.ErroCampo;
import com.library.libraryapi.controller.dto.ErroResponse;
import com.library.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.library.libraryapi.exceptions.RegistroCadastradoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listErrors = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .toList();
        return new ErroResponse("Erro de Validação", HttpStatus.UNPROCESSABLE_ENTITY.value(), listErrors);
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse handlerOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e) {
        return ErroResponse.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RegistroCadastradoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResponse handlerRegistroCadastradoException(RegistroCadastradoException e) {
        return ErroResponse.conflito(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResponse handlerGeneric(Exception e) {
        return new ErroResponse("Ocorreu um erro inesperado. Entrar em contato com a administração",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                List.of());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResponse handlerEntityNotFoundException(EntityNotFoundException e) {
        return new ErroResponse("Entidade não encontrada", HttpStatus.NOT_FOUND.value(), List.of());
    }
}
