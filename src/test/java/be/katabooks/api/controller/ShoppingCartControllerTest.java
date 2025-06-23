package be.katabooks.api.controller;

import be.katabooks.api.model.Book;
import be.katabooks.api.model.ShoppingCart;
import be.katabooks.api.model.User;
import be.katabooks.api.repository.BookRepository;
import be.katabooks.api.repository.ShoppingCartRepository;
import be.katabooks.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ShoppingCartControllerTest {

    private UserRepository userRepository;
    private ShoppingCartRepository cartRepository;
    private BookRepository bookRepository;
    private ShoppingCartController controller;
    private User user;
    private Book book;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        cartRepository = Mockito.mock(ShoppingCartRepository.class);
        bookRepository = Mockito.mock(BookRepository.class);
        controller = new ShoppingCartController(userRepository, cartRepository, bookRepository);

        user = new User();
        user.setUsername("testuser");
        book = new Book();
        book.setCode("BOOK123");

        // Mock authentication context
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser");
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepository.findByUsername("testuser")).thenReturn(user);
    }

    @Test
    void addToCart_AddsNewCartItem() {
        when(bookRepository.findById("BOOK123")).thenReturn(Optional.of(book));
        when(cartRepository.findByUserAndBook(user, book)).thenReturn(Optional.empty());
        ShoppingCart cart = new ShoppingCart();
        cart.setBook(book);
        cart.setUser(user);
        cart.setQuantity(2);
        when(cartRepository.save(any(ShoppingCart.class))).thenReturn(cart);

        ShoppingCart result = controller.addToCart("BOOK123", 2);

        assertEquals(book, result.getBook());
        assertEquals(2, result.getQuantity());
        verify(cartRepository).save(any(ShoppingCart.class));
    }

    @Test
    void addToCart_UpdatesExistingCartItem() {
        when(bookRepository.findById("BOOK123")).thenReturn(Optional.of(book));
        ShoppingCart existing = new ShoppingCart();
        existing.setBook(book);
        existing.setUser(user);
        existing.setQuantity(1);
        when(cartRepository.findByUserAndBook(user, book)).thenReturn(Optional.of(existing));
        when(cartRepository.save(existing)).thenReturn(existing);

        ShoppingCart result = controller.addToCart("BOOK123", 3);

        assertEquals(4, result.getQuantity());
        verify(cartRepository).save(existing);
    }

    @Test
    void getCart_ReturnsUserCart() {
        ShoppingCart cartItem = new ShoppingCart();
        when(cartRepository.findByUser(user)).thenReturn(Collections.singletonList(cartItem));

        Iterable<ShoppingCart> result = controller.getCart();
        assertTrue(result.iterator().hasNext());
        verify(cartRepository).findByUser(user);
    }

    @Test
    void clearCart_DeletesAllUserCartItems() {
        ShoppingCart cart1 = new ShoppingCart();
        ShoppingCart cart2 = new ShoppingCart();
        when(cartRepository.findByUser(user)).thenReturn(Arrays.asList(cart1, cart2));

        controller.clearCart();

        verify(cartRepository).delete(cart1);
        verify(cartRepository).delete(cart2);
    }

    @Test
    void updateCart_UpdatesQuantity() {
        when(bookRepository.findById("BOOK123")).thenReturn(Optional.of(book));
        ShoppingCart cart = new ShoppingCart();
        cart.setBook(book);
        cart.setUser(user);
        cart.setQuantity(1);
        when(cartRepository.findByUserAndBook(user, book)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        ShoppingCart result = controller.updateCart("BOOK123", 5);

        assertEquals(5, result.getQuantity());
        verify(cartRepository).save(cart);
    }

    @Test
    void removeFromCart_RemovesCartItem() {
        when(bookRepository.findById("BOOK123")).thenReturn(Optional.of(book));
        ShoppingCart cart = new ShoppingCart();
        cart.setBook(book);
        cart.setUser(user);
        when(cartRepository.findByUserAndBook(user, book)).thenReturn(Optional.of(cart));

        controller.removeFromCart("BOOK123");

        verify(cartRepository).delete(cart);
    }
}