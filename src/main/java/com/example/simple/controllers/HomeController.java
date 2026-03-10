package com.example.simple.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(
            HttpServletRequest request,
            Model model
    ) {
        var userLoggedIn = Utils.isUserLoggedIn(request);
        model.addAttribute("userLoggedIn", userLoggedIn);
        return "home";
    }
}
