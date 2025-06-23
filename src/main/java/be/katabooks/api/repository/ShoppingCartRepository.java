package be.katabooks.api.repository;

import be.katabooks.api.model.Book;
import be.katabooks.api.model.ShoppingCart;
import be.katabooks.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
    Iterable<ShoppingCart> findByUser(User user);

    Optional<ShoppingCart> findByUserAndBook(User user, Book book);
}
