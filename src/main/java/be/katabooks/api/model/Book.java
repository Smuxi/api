package be.katabooks.api.model;

import be.katabooks.api.QuantityException;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    @Id
    private String code;
    private String title;
    private String author;
    private double price;

    public Book() {}

    public Book(String code, String title, String author, double price) {
        this.code = code;
        this.title = title;
        this.author = author;
        setPrice(price);
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new QuantityException();
        }
        this.price = price;
    }
}
