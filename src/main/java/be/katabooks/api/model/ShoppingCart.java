package be.katabooks.api.model;

import be.katabooks.api.QuantityException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_code", referencedColumnName = "code")
    private Book book;
    private int quantity;

    public ShoppingCart() {}

    public ShoppingCart(Book book, int quantity) {
        this.book = book;
        setQuantity(quantity);
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new QuantityException();
        }
        this.quantity = quantity;
    }
}
