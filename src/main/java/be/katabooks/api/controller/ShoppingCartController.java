package be.katabooks.api.controller;

import be.katabooks.api.BookNotFoundException;
import be.katabooks.api.QuantityException;
import be.katabooks.api.model.*;
import be.katabooks.api.repository.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final UserRepository userRepository;
    private final ShoppingCartRepository cartRepository;
    private final BookRepository bookRepository;

    public ShoppingCartController(UserRepository userRepository, ShoppingCartRepository cartRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/add")
    public ShoppingCart addToCart(@RequestParam String bookCode, @RequestParam int quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username); //.orElseThrow();

        Book book = bookRepository.findById(bookCode)
                .orElseThrow(() -> new BookNotFoundException(bookCode));

        ShoppingCart cart = cartRepository.findByUserAndBook(user, book).orElse(null);

        if (cart != null) {
            // If the book is already in the cart, update the quantity
            cart.setQuantity(cart.getQuantity() + quantity);
        } else {
            // If the book is not in the cart, create a new entry
            cart = new ShoppingCart();
            cart.setUser(user);
            cart.setBook(book);
            cart.setQuantity(quantity);
        }

        return cartRepository.save(cart);
    }

    @GetMapping
    public Iterable<ShoppingCart> getCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);//.orElseThrow();

        return cartRepository.findByUser(user);
    }

    @DeleteMapping
    public void clearCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username); //.orElseThrow();

        Iterable<ShoppingCart> carts = cartRepository.findByUser(user);
        for (ShoppingCart cart : carts) {
            cartRepository.delete(cart);
        }
    }

    @PutMapping
    public ShoppingCart updateCart(@RequestParam String bookCode, @RequestParam int quantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username); //.orElseThrow();

        Book book = bookRepository.findById(bookCode)
                .orElseThrow(() -> new BookNotFoundException(bookCode));

        ShoppingCart cart = cartRepository.findByUserAndBook(user, book).orElseThrow();

        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    @DeleteMapping
    @RequestMapping("/remove/{bookCode}")
    public void removeFromCart(@PathVariable String bookCode) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username); //.orElseThrow();

        Book book = bookRepository.findById(bookCode)
                .orElseThrow(() -> new BookNotFoundException(bookCode));

        ShoppingCart cart = cartRepository.findByUserAndBook(user, book).orElseThrow();

        cartRepository.delete(cart);
    }
}