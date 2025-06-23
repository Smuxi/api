package be.katabooks.api.controller;

import be.katabooks.api.model.Book;
import be.katabooks.api.repository.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    private final BookRepository repository;

    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/books")
    List<Book> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

}
