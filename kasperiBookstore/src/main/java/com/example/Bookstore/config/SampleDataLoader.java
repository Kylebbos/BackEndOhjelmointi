package com.example.Bookstore.config;

import com.example.Bookstore.domain.Book;
import com.example.Bookstore.domain.BookRepository;
import com.example.Bookstore.domain.Category;
import com.example.Bookstore.domain.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
public class SampleDataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public SampleDataLoader(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        Category category1 = new Category("Fiction");
        Category category2 = new Category("Science Fiction");
        Category category3 = new Category("Mystery");
        Category category4 = new Category("Romance");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);

        
        Book book1 = new Book("Book 1", "Matti Pena", 2023, "ISBN-1", 29.99);
        book1.setCategory(category1);

        Book book2 = new Book("Book 2", "Kassu Kessu", 2022, "ISBN-2", 19.99);
        book2.setCategory(category2);

        bookRepository.save(book1);
        bookRepository.save(book2);
    }
}
