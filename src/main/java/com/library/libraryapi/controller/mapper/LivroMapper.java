package com.library.libraryapi.controller.mapper;

import com.library.libraryapi.controller.dto.LivroDTO;
import com.library.libraryapi.controller.dto.LivroResponseDTO;
import com.library.libraryapi.model.Livro;
import com.library.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java(autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity(LivroDTO dto);

    public abstract LivroResponseDTO toDto(Livro livro);
}
