package com.library.libraryapi.controller;

import com.library.libraryapi.controller.dto.LivroDTO;
import com.library.libraryapi.controller.dto.LivroResponseDTO;
import com.library.libraryapi.controller.mapper.LivroMapper;
import com.library.libraryapi.model.Autor;
import com.library.libraryapi.model.Genero;
import com.library.libraryapi.model.Livro;
import com.library.libraryapi.service.AutorService;
import com.library.libraryapi.service.LivroService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;
    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid LivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        this.service.salvar(livro);
        URI uri = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> obterPorId(@PathVariable String id) {
        return this.service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    LivroResponseDTO livroResponseDTO = mapper.toDto(livro);
                    return ResponseEntity.ok(livroResponseDTO);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        return this.service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    this.service.deletarLivro(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<LivroResponseDTO>> pesquisar(@RequestParam(value = "isbn", required = false) String isbn,
                                                            @RequestParam(value = "titulo", required = false) String titulo,
                                                            @RequestParam(value = "nome-autor", required = false) String nomeAutor,
                                                            @RequestParam(value = "genero", required = false) Genero genero,
                                                            @RequestParam(value = "data-publicacao", required = false) Integer dataPublicacao,
                                                            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
                                                            @RequestParam(value = "tamanho-pagina", defaultValue = "2") Integer tamanhoPagina){
        Page<Livro> livros = this.service.pesquisaSpecification(isbn, titulo, nomeAutor, genero, dataPublicacao, pagina, tamanhoPagina);

        Page<LivroResponseDTO> livroPage = livros.map(mapper::toDto);

        return ResponseEntity.ok(livroPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id,
                                            @RequestBody @Valid LivroDTO dto) {
        return service.obterPorId(UUID.fromString(id)).map(livro -> {
            Autor autor = autorService.buscarPorId(dto.idAutor()).orElseThrow(EntityNotFoundException::new);
            Livro entity = mapper.toEntity(dto);

            entity.setId(livro.getId());
            entity.setAutor(autor);

            entity.setDataAtualizacao(livro.getDataAtualizacao());
            entity.setDataCriacao(livro.getDataCriacao());
            entity.setIdUsuario(livro.getIdUsuario());

            service.atualizar(entity);

            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
