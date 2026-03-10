package com.example.simple.controllers;

import com.example.simple.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginGet(
            HttpServletRequest request
    ) {
        if (Utils.isUserLoggedIn(request)) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(
            @RequestParam("username") Optional<String> usernameForm,
            @RequestParam("password") Optional<String> passwordForm,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {
        if (Utils.isUserLoggedIn(request)) {
            return "redirect:/";
        }
        var username = usernameForm.orElse("");
        var password = passwordForm.orElse("");
        if (loginService.checkLogin(username, password)) {
            var token = Utils.createToken(username);
            var cookie = Utils.mkResponseCookie(token);
            response.addHeader("Set-Cookie", cookie.toString());
            return "redirect:/";
        }
        model.addAttribute("errorMsg", "Wrong username or password")
                .addAttribute("username", username)
                .addAttribute("password", password);
        return "login";
    }

}
