package com.library.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record   AutorDTO(@NotBlank(message = "Campo Obrigatório")
                         @Size(min = 2, max = 255)
                         String nome,
                         @NotNull(message = "Campo Obrigatório")
                         @Past(message = "Não pode ser data futura")
                         LocalDate dataNascimento,
                         @NotBlank(message = "Campo Obrigatório")
                         @Size(min = 2, max = 100)
                         String nacionalidade) {
}
