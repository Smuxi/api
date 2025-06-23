package be.katabooks.api.controller;

import be.katabooks.api.model.Book;
import be.katabooks.api.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookControllerTest {

    @Test
    void all_ReturnsAllBooks() {
        // Arrange
        BookRepository mockRepo = Mockito.mock(BookRepository.class);
        List<Book> books = Arrays.asList(
                new Book("123", "Book 1", "Author 1", 10.0),
                new Book("456", "Book 2", "Author 2", 20.0)
        );
        Mockito.when(mockRepo.findAll()).thenReturn(books);

        BookController controller = new BookController(mockRepo);

        // Act
        List<Book> result = controller.all();

        // Assert
        assertEquals(books, result);
    }
}