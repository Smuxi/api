package be.katabooks.api;

import be.katabooks.api.model.Book;
import be.katabooks.api.repository.BookRepository;
import be.katabooks.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(BookRepository bookRepository, UserRepository userRepository) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoded = encoder.encode("tu");

        return args -> {
            log.info("Preloading " + bookRepository.save(new Book("1234567890", "The Hobbit", "J.R.R. Tolkien", 19.37)));
            log.info("Preloading " + bookRepository.save(new Book("0987654321", "The Lord of the Rings", "J.R.R. Tolkien", 29.99)));
        };
    }
}