package com.library.libraryapi.controller.dto;

import com.library.libraryapi.model.Genero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroDTO(
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        Genero genero,
        BigDecimal preco,
        UUID idAutor
        ) {
}
