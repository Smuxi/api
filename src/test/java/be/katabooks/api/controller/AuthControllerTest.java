package be.katabooks.api.controller;

import be.katabooks.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void register_WhenUserServiceThrows_ThrowsBadRequest() {
        UserService userService = Mockito.mock(UserService.class);
        AuthController controller = new AuthController(userService);
        Mockito.doThrow(new RuntimeException("Registration failed")).when(userService).registerUser("failuser", "failpass");
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.register("failuser", "failpass"));
        assertEquals(org.springframework.http.HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Registration failed", ex.getReason());
    }

    @Test
    void login_WhenLoginFails_ThrowsUnauthenticatedException() {
        UserService userService = Mockito.mock(UserService.class);
        AuthController controller = new AuthController(userService);
        Mockito.when(userService.loginUser("baduser", "badpass")).thenReturn(false);
        assertThrows(be.katabooks.api.UnauthenticatedException.class, () -> controller.login("baduser", "badpass"));
    }
}