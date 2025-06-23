package be.katabooks.api.controller;

import be.katabooks.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthControllerTest {

    @Test
    void register_CallsUserServiceAndReturnsMessage() {
        // Arrange
        UserService userService = Mockito.mock(UserService.class);
        AuthController controller = new AuthController(userService);

        // Act
        String result = controller.register("testuser", "testpass");

        // Assert
        Mockito.verify(userService).registerUser("testuser", "testpass");
        assertEquals("User registered", result);
    }
}