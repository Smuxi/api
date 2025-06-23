package be.katabooks.api.controller;

import be.katabooks.api.UnauthenticatedException;
import be.katabooks.api.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        userService.registerUser(username, password);
        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean success = userService.loginUser(username, password);
        if (!success) {
            throw new UnauthenticatedException();
        }

        return "Login successful";
    }
}