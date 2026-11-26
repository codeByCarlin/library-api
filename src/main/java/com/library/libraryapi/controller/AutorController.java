package com.library.libraryapi.controller;

import com.library.libraryapi.controller.dto.AutorDTO;
import com.library.libraryapi.controller.dto.AutorResponseDTO;
import com.library.libraryapi.controller.mapper.AutorMapper;
import com.library.libraryapi.model.Autor;
import com.library.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService autorService;

    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDTO) {

        Autor autor = mapper.toEntity(autorDTO);
        autorService.salvar(autor);

            /*
            Isso é para pegar a URI do servidor, então ele pega a requisição atual qual vai ser o caminho
            do recurso a ser criado, depois ele joga o id no caminho e depois transforma para URI.
            Logo fica assim:
            http://localhost:8080/autores/id que vai ser gerado
            */

        URI uri = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> buscarPorId(@PathVariable String id) {
        UUID autorId = UUID.fromString(id);
        return autorService.buscarPorId(autorId)
                .map(autor -> {
                    AutorResponseDTO dto = mapper.toResponseDTO(autor);
                    return ResponseEntity.ok().body(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {

        UUID idAutor = UUID.fromString(id);
        Optional<Autor> autor = autorService.buscarPorId(idAutor);

        if (autor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        autorService.deletar(autor.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> pesquisar(@RequestParam(value = "nome", required = false) String nome,
                                                            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> pesquisar = this.autorService.pesquisarExample(nome, nacionalidade);

        List<AutorResponseDTO> list = pesquisar.stream()
                .map(mapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO autorDTO) {

        UUID uuid = UUID.fromString(id);
        Optional<Autor> autor = autorService.buscarPorId(uuid);

        if (autor.isEmpty()) return ResponseEntity.notFound().build();

        Autor autorAtualizado = autor.get();
        autorAtualizado.setNome(autorDTO.nome());
        autorAtualizado.setNacionalidade(autorDTO.nacionalidade());
        autorAtualizado.setDataNascimento(autorDTO.dataNascimento());

        autorService.atualizar(autorAtualizado);
        return ResponseEntity.noContent().build();
    }
}
