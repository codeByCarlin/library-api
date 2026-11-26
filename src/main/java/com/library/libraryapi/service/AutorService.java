package com.library.libraryapi.service;

import com.library.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.library.libraryapi.model.Autor;
import com.library.libraryapi.repository.AutorRepository;
import com.library.libraryapi.repository.LivroRepository;
import com.library.libraryapi.validator.AutorValidator;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    private final AutorRepository repository;

    private final AutorValidator validator;

    private final LivroRepository livroRepository;

    public AutorService(AutorRepository repository, AutorValidator validator, LivroRepository livroRepository) {
        this.repository = repository;
        this.validator = validator;
        this.livroRepository = livroRepository;
    }

    public void salvar(Autor autor) {
        this.validator.validar(autor);
        this.repository.save(autor);
    }

    public Optional<Autor> buscarPorId(UUID id) {
        return repository.findById(id);
//                .map(autor -> new AutorResponseDTO(
//                        autor.getId(),
//                        autor.getNome(),
//                        autor.getDataNascimento(),
//                        autor.getNacionalidade()
//                ));
    }

    public void deletar(Autor autor) {
        if (possuiLivros(autor)) {
            throw new OperacaoNaoPermitidaException("Não é permitido deletar Autor com livros");
        }
        repository.delete(autor);
    }

//    public List<Autor> pesquisar(String nome, String nacionalidade) {
//        if (nome != null && nacionalidade != null) {
//            return this.repository.findByNomeAndNacionalidade(nome, nacionalidade);
//        } else if (nome != null) {
//            return this.repository.findByNome(nome);
//        } else if (nacionalidade != null){
//            return this.repository.findByNacionalidade(nacionalidade);
//        }
//        return this.repository.findAll();
//    }

    public List<Autor> pesquisarExample(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id", "dataNascimento", "dataCriacao")
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Autor> autorExample = Example.of(autor, exampleMatcher);

        return repository.findAll(autorExample);
    }

    public void atualizar(Autor autor) {
        if (autor == null) {
            throw new IllegalArgumentException("Não passou o Autor!");
        }
        this.validator.validar(autor);
        this.repository.save(autor);

    }

    public boolean possuiLivros(Autor autor) {
        return this.livroRepository.existsByAutor(autor);
    }
}
