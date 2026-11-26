package com.library.libraryapi.repository.specs;

import com.library.libraryapi.model.Genero;
import com.library.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEquals(String isbn) {
        return ((root, query, cb) -> cb.equal(root.get("isbn"), isbn));
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return ((root, query, cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%"));
    }

    public static Specification<Livro> generoEqual(Genero genero) {
        return ((root, query, cb) -> cb.equal(root.get("genero"), genero));
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer dataPublicacao) {
        //to_char(data_publicacao, 'YYYY') from livros ou melhor :dataPublicacao
        return (root, query, cb) ->
                cb.equal(
                    cb.function("to_char", String.class, root.get("dataPublicacao"), cb.literal("YYYY")
                ), dataPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nomeAutor) {
// return ((root, query, cb) -> cb.like(cb.upper(root.get("autor").get("nome")), "%" + nomeAutor.toUpperCase() + "%"));

        return ((root, query, cb) -> {
            Join<Object, Object> join = root.join("autor", JoinType.INNER);
            return cb.like(cb.upper(join.get("nome")), "%" + nomeAutor.toUpperCase() + "%");
        });
    }
}
