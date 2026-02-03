package com.example.Bookstore.config;

import com.example.Bookstore.domain.Category;
import com.example.Bookstore.domain.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryDataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoryDataLoader(CategoryRepository categoryRepository) {
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
    }
}

