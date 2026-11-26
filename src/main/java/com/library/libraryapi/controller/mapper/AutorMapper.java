package com.library.libraryapi.controller.mapper;

import com.library.libraryapi.controller.dto.AutorDTO;
import com.library.libraryapi.controller.dto.AutorResponseDTO;
import com.library.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDto(Autor autor);

    AutorResponseDTO toResponseDTO(Autor autor);
}
