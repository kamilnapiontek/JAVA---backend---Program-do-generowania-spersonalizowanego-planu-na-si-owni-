package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.RejestracjaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // zapytania z API
@RequestMapping("login")  // ścieżka dostępów
@RequiredArgsConstructor

public class LoginController {
    private final UserService userService;
    @PostMapping
    public void login (@RequestBody RejestracjaRequest rejestracjaRequest)
    {
        userService.log_in(rejestracjaRequest);
    }
    @GetMapping
    public User writeUser()
    {
        return userService.findAll().get(0);
    }
}
