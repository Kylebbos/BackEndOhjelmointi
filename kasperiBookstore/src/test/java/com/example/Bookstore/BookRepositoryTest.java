package com.example.Bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.Bookstore.domain.Book;
import com.example.Bookstore.domain.BookRepository;
import com.example.Bookstore.domain.Category;
import com.example.Bookstore.domain.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findByAuthorShouldReturnBooks() {
        
        Category category = new Category("Romance");
        categoryRepository.save(category);

        Book book = new Book("Test Book", "Matti Pena", 2023, "ISBN-123", 29.99);
        book.setCategory(category);
        repository.save(book);

        List<Book> booksByAuthor = repository.findByAuthor("Matti Pena");
        
        assertThat(booksByAuthor).hasSize(1);
        assertThat(booksByAuthor.get(0).getTitle()).isEqualTo("Test Book");
    }

    @Test
    public void createNewBook() {
        Category category = new Category("Romance");
        categoryRepository.save(category);
        Book book = new Book("Book 3", "Kuusi Kassu", 2023 , "ISBN-3", 30.00);
        book.setCategory(category);
        repository.save(book);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void deleteBook() {
        Category category = new Category("Romance");
        categoryRepository.save(category);

        Book book = new Book("Test Book", "Matti Pena", 2023, "ISBN-123", 29.99);
        book.setCategory(category);
        repository.save(book);

        repository.delete(book);
        List<Book> newBooks = repository.findByAuthor("Matti Pena");
        assertThat(newBooks).isEmpty();
    }
}
