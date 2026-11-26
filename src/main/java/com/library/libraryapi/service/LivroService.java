package com.library.libraryapi.service;

import com.library.libraryapi.model.Genero;
import com.library.libraryapi.model.Livro;
import com.library.libraryapi.repository.LivroRepository;
import com.library.libraryapi.repository.specs.LivroSpecs;
import com.library.libraryapi.validator.LivroValidator;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;

    public LivroService(LivroRepository repository, LivroValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public void salvar(Livro livro) {
        this.validator.validar(livro);
        this.repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return this.repository.findById(id);
    }

    public void deletarLivro(Livro livro) {
        this.repository.delete(livro);
    }

    public Page<Livro> pesquisaSpecification(String isbn,
                                             String titulo,
                                             String nomeAutor,
                                             Genero genero,
                                             Integer dataPublicacao,
                                             Integer pagina,
                                             Integer tamanhoPagina) {

        // select * from livro where 0 = 0
        Specification<Livro> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if (StringUtils.hasText(isbn)) {
            specs = specs.and(LivroSpecs.isbnEquals(isbn));
        }

        if (StringUtils.hasText(titulo)) {
            specs = specs.and(LivroSpecs.tituloLike(titulo));
        }

        if (genero != null) {
            specs = specs.and(LivroSpecs.generoEqual(genero));
        }

        if (dataPublicacao != null) {
            specs = specs.and(LivroSpecs.anoPublicacaoEqual(dataPublicacao));
        }

        if (StringUtils.hasText(nomeAutor)) {
            specs = specs.and(LivroSpecs.nomeAutorLike(nomeAutor));
        }

        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, pageRequest);
    }

    @Transactional
    public void atualizar(Livro livro) {
        if (livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar livro precisa ja estar cadastrado");
        }

        this.validator.validar(livro);
        this.repository.save(livro);
    }
}
