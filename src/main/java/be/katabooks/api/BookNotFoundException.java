package be.katabooks.api;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String code) {
        super("Could not find book " + code);
    }
}
