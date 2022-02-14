package com.example.demo.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RejestracjaRequest {
    
    private String login;
    private String password;

    public RejestracjaRequest (String login, String password)
    {
        this.login=login;
        this.password=password;
    }
}
