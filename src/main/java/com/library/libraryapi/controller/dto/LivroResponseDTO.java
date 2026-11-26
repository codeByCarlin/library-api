package com.library.libraryapi.controller.dto;

import com.library.libraryapi.model.Genero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LivroResponseDTO(
        UUID id,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        Genero genero,
        BigDecimal preco,
        AutorDTO autor
        ){
}
