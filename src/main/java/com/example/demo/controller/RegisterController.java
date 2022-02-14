package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.RejestracjaRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController // zapytania z API
@RequestMapping("registration")  // ścieżka dostępów
@RequiredArgsConstructor
@Slf4j // umożliwia logowanie

    // METODY API
public class RegisterController
{
    private final UserService userService;

    // ZAPISYWANIE UŻYTKOWNIKA W BAZIE
    @PostMapping
    public void registration (@RequestBody RejestracjaRequest rejestracjaRequest)
    {
        userService.reqister(rejestracjaRequest);
    }
    @GetMapping
    public void getUserById(int id)
    {
        final var userById = userService.findAll();
        for(User user : userById){
            if(user.getId() == id){
                log.info(user.toString());
            }
        }
        final List<User> collect = userById.stream().filter(u -> u.getId() == id).collect(Collectors.toList());
        User user2 = userById.stream().filter(u -> u.getId() == id).findFirst().get();
        log.info(collect.get(0).toString());

    }




}
