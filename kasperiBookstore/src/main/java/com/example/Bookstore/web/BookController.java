package com.example.Bookstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.Bookstore.domain.Book;
import com.example.Bookstore.domain.BookRepository;
import com.example.Bookstore.domain.Category;
import com.example.Bookstore.domain.CategoryRepository;

@Controller
public class BookController {

	private final BookRepository bookRepository;

	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping("/index")
	public String index(Model model) {

		List<Book> books = new ArrayList<>();
		books.add(new Book("Book 1", "Matti Pena", 2023, "ISBN-1", 29.99));
		books.add(new Book("Book 2", "Kassu Kessu", 2022, "ISBN-2", 19.99));

		model.addAttribute("books", books);

		return "index";
	}

	@GetMapping("/booklist")
	public String bookList(Model model) {
		Iterable<Book> books = bookRepository.findAll();
		model.addAttribute("books", books);
		return "booklist";
	}
	
	@GetMapping("/addbook")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAddBookForm(Model model) {
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "addbook";
    }
	
	@PostMapping("/addbook")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/booklist";
    }


	@PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/booklist";
    }

	@GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editBook(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        model.addAttribute("book", book);
        return "editbook";
    }

	@PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book updatedBook) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        bookRepository.save(book);
        return "redirect:/booklist";
    }

	@GetMapping("/api/books")
	public @ResponseBody List<Book> getAllBooks() {
		return (List<Book>) bookRepository.findAll();
	}

	@GetMapping("/api/books/{id}")
	public @ResponseBody ResponseEntity<Book> getBookById(@PathVariable Long id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isPresent()) {
			return new ResponseEntity<>(book.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
