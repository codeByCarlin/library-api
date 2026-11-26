package com.library.libraryapi.repository;

import com.library.libraryapi.model.Autor;
import com.library.libraryapi.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    Page<Livro> findByAutor(Autor autor, Pageable pageable);

    //Query Method

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    @Query(value = """
            select *
            from livro
            where to_tsvector('portuguese', coalesce(titulo, '') || ' ' || coalesce(genero, '')) @@ plainto_tsquery('portuguese', :busca)             
            """, nativeQuery = true)
    List<Livro> listarTitulo(@Param("busca") String busca);

    boolean existsByAutor(Autor autor);

    Optional<Livro> findByIsbn(String isbn);
}
