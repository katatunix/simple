package com.example.simple.services;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public boolean checkLogin(String username, String password) {
        return username.equals("admin") && password.equals("123456");
    }
}
