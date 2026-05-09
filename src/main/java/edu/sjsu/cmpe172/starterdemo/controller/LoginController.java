package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.User;
import edu.sjsu.cmpe172.starterdemo.service.LoginService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public Object login(@RequestBody Map<String, String> body) {
        // get login info from frontend request
        String email = body.get("email");
        String password = body.get("password");

        User user = loginService.login(email, password);

        // if login fails, return error message
        if (user == null) {
            return Map.of(
                    "success", false,
                    "message", "Invalid email or password"
            );
        }

        // if login successful, return user info
        return Map.of(
                "success", true,
                "userId", user.getUserId(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "email", user.getEmail(),
                "role", user.getRole()
        );
    }
}