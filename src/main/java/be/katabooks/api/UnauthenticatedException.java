package be.katabooks.api;

public class UnauthenticatedException extends RuntimeException {

    public UnauthenticatedException() {
        super("Invalid credentials");
    }
}
