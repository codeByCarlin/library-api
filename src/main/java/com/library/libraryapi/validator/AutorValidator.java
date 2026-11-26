package com.library.libraryapi.validator;

import com.library.libraryapi.exceptions.RegistroCadastradoException;
import com.library.libraryapi.model.Autor;
import com.library.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroCadastradoException("Autor já Cadastrado");
        }
    }

    private boolean existeAutorCadastrado(Autor autor) {
        Optional<Autor> autorPossivel = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade());

        //Saber se o autor que estou enviando de fora não foi cadastrado, faço a prova de retorno true ou false se ja existir
        //um autor cadastrado no banco com os mesmos valores, então vai dar conflito se já existir, se não posso cadastrar o novo autor.
        if (autor.getId() == null) {
            return autorPossivel.isPresent();
        }
/*
    Buscar os dados do autor de fora no banco de dados pra saber se ja existe um autor com os mesmos dados ja cadastrados no banco de dados
    Se existir um autor no banco com os mesmos dados, vou saber se é o mesmo autor que estou enviando de fora, tirando a prova pelo identificador(ID)
    Se não for, vai dar conflito, ja que vou ter dois autores distintos com o mesmo dado no banco
*/
        return autorPossivel.isPresent() && !autor.getId().equals(autorPossivel.get().getId());
    }
}
