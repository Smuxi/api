package be.katabooks.api;

public class QuantityException extends RuntimeException {

    public QuantityException() {
        super("Quantity must be greater than zero");
    }
}
