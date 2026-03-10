package com.example.simple.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(
            HttpServletRequest request,
            Model model,
            @Value("${MYSQLHOST}") String mysqlHost,
            @Value("${MYSQLPORT}") int mysqlPort,
            @Value("${MYSQLUSER}") String mysqlUsername,
            @Value("${MYSQLPASSWORD}") String mysqlPassword
    ) {
        System.out.println(mysqlHost);
        System.out.println(mysqlPort);
        System.out.println(mysqlUsername);
        System.out.println(mysqlPassword);
        var userLoggedIn = Utils.isUserLoggedIn(request);
        model.addAttribute("userLoggedIn", userLoggedIn);
        return "home";
    }
}
