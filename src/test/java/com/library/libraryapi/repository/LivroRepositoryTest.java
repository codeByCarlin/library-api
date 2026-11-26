package com.library.libraryapi.repository;

import com.library.libraryapi.model.Autor;
import com.library.libraryapi.model.Genero;
import com.library.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LivroRepositoryTest {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    public void salvar() {
        Livro livro = new Livro();

        livro.setIsbn("0234213412312");
        livro.setPreco(BigDecimal.valueOf(50.00));
        livro.setGenero(Genero.ROMANCE);
        livro.setTitulo("Kid Bengala na Alemanha");
        livro.setDataPublicacao(LocalDate.of(1998, 5, 13));

        Autor autor = autorRepository
                .findById(UUID.fromString("0f21c61e-4c2a-4cca-bbbb-39a1c9d3e342"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    public void salvarCascade() {
        Livro livro = new Livro();

        livro.setIsbn("1234567876543");
        livro.setPreco(BigDecimal.valueOf(150.50));
        livro.setGenero(Genero.MAGIA);
        livro.setTitulo("Familia Sacana");
        livro.setDataPublicacao(LocalDate.of(2020, 3, 20));

        Autor autor = new Autor();

        autor.setNome("Carlos");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1953,5, 2));

        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    public void salvarAutoreLivro() {
        Livro livro = new Livro();

        livro.setIsbn("2345678902543");
        livro.setPreco(BigDecimal.valueOf(150.50));
        livro.setGenero(Genero.MAGIA);
        livro.setTitulo("Mia Khalifa e a Aventura Foda");
        livro.setDataPublicacao(LocalDate.of(2021, 1, 30));

        Autor autor = new Autor();

        autor.setNome("Pedro");
        autor.setNacionalidade("Libanes");
        autor.setDataNascimento(LocalDate.of(2000,6, 23));
        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);

    }

    @Test
    public void atualizarAutorDoLivro() {
        UUID id = UUID.fromString("a7da2725-2557-43e8-93be-af1045200a3e");
        Livro livro = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("00498ef4-8981-4746-af1d-11dd497110a3");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    public void deletar() {
        UUID uuid = UUID.fromString("a7da2725-2557-43e8-93be-af1045200a3e");
        repository.deleteById(uuid);
    }

    @Test
    public void deletarCascade() {
        UUID uuid = UUID.fromString("fd58bb8f-b58c-435f-b532-bdb3cd2b0b4c");
        repository.deleteById(uuid);
    }

    @Test
    public void listarPorTitulo() {
        List<Livro> livros = repository.findByTituloContainingIgnoreCase("a");
        livros.forEach(System.out::println);
    }

    @Test
    public void listarPorTituloOtimizado() {
        List<Livro> livros = repository.listarTitulo("menino  ");
        livros.forEach(System.out::println);

    }
}