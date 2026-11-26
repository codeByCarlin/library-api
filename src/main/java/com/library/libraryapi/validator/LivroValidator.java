package com.library.libraryapi.validator;

import com.library.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.library.libraryapi.exceptions.RegistroCadastradoException;
import com.library.libraryapi.model.Livro;
import com.library.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {
    private static final int ANO_LIMITE = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro) {
        if(existeLivroComIsbnNoBanco(livro)){
            throw new RegistroCadastradoException("Livro já cadastrado!");
        }

        if (precoObrigatorioNulo(livro)) {
            throw new OperacaoNaoPermitidaException("Livros com ano de publicação a partir de 2020 devem ter preço informado", "preço");
        }
    }

    private boolean precoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null && livro.getDataPublicacao().getYear() >= ANO_LIMITE;
    }

    private boolean existeLivroComIsbnNoBanco(Livro livro) {
        Optional<Livro> livroOptional = repository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null) {
            return livroOptional.isPresent();
        }

        return livroOptional.map(Livro::getId).stream().anyMatch(id -> !id.equals(livro.getId()));

    }
}
