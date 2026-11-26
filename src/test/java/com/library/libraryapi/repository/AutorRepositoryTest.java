package com.library.libraryapi.repository;

import com.library.libraryapi.model.Autor;
import com.library.libraryapi.model.Genero;
import com.library.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private LivroRepository livroRepository;

//    public AutorRepositoryTest(AutorRepository repository) {
//        this.repository = repository;
//    }

    @Test
    public void salvarAutor() {
        Autor autor = new Autor();

        autor.setNome("José Dirceu");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950, 5, 1));

        Autor save = repository.save(autor);
        System.out.println("Autor salvo: " + save);
    }

//    @Test
    public void atualizarAutor() {
        UUID uuid = UUID.fromString("29a871c3-71eb-42bd-ac45-8eef94e77844");
        Optional<Autor> byId = repository.findById(uuid);

        if (byId.isPresent()) {
            Autor autorOptional = byId.get();
            System.out.println("Dados do autor:");
            System.out.println(autorOptional);

            autorOptional.setDataNascimento(LocalDate.of(1961, 3, 13));

            repository.save(autorOptional);
        }
    }

    @Test
    public void listarAutor() {
        List<Autor> autores = repository.findAll();
        autores.forEach(System.out::println);

//        Optional<Autor> byId = repository.findById(UUID.fromString("0c05a831-9a1f-4dcc-a0e7-786eaa87dbaa"));
//        System.out.println(byId.get());
    }

    @Test
    public void contarAutor() {
        System.out.println("Quant de autores: " + repository.count());
    }

    @Test
    public void deletarAutorPorId() {
        UUID id = UUID.fromString("4d85a45b-a930-4877-a257-365db789b1f0");
        repository.deleteById(id);
    }

    @Test
    public void deletarPorAutor() {
        UUID id = UUID.fromString("0c05a831-9a1f-4dcc-a0e7-786eaa87dbaa");
        Autor autor = repository.findById(id).get();
        repository.delete(autor);
    }

    @Test
    public void salvarAutorComLivrosTest() {
        Autor autor = new Autor();

        autor.setNome("Mariano");
        autor.setNacionalidade("Japones");
        autor.setDataNascimento(LocalDate.of(1980, 4, 17));

        Livro livro = new Livro();
        livro.setIsbn("1234567891234");
        livro.setTitulo("O menino da vara sombria");
        livro.setDataPublicacao(LocalDate.of(1990, Month.FEBRUARY, 28));
        livro.setPreco(BigDecimal.valueOf(100.25));
        livro.setGenero(Genero.SUSPENSE);
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("1234563212342");
        livro2.setTitulo("O menino da vara clara");
        livro2.setDataPublicacao(LocalDate.of(1989, Month.DECEMBER, 22));
        livro2.setPreco(BigDecimal.valueOf(200.50));
        livro2.setGenero(Genero.ROMANCE);
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        livroRepository.saveAll(autor.getLivros());

        repository.save(autor);
    }

    @Test
    public void listarLivrosAutor() {
        UUID id = UUID.fromString("4ff50a32-bb81-4e89-91cd-3c4788075964");
        Autor autor = repository.findById(id).get();

        List<Livro> livros = livroRepository.findByAutor(autor);
        autor.setLivros(livros);

        autor.getLivros().forEach(System.out::println);
    }
}
