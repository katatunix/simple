package com.example.simple.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

import java.time.Instant;

class Utils {
    public static boolean isUserLoggedIn(HttpServletRequest request) {
        var cookies = request.getCookies();
        if (cookies == null) return false;
        for (var cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                return verifyToken(cookie.getValue());
            }
        }
        return false;
    }

    public static ResponseCookie mkResponseCookie(String token) {
        return ResponseCookie.from("token", token).build();
    }

    private static final Algorithm algo = Algorithm.HMAC256("coco-melon");

    public static String createToken(String username) {
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(Instant.now().plusSeconds(3600))
                .sign(algo);
    }

    public static boolean verifyToken(String token) {
        var verifier = JWT.require(algo).build();
        try {
            var jwt = verifier.verify(token);
            var username = jwt.getClaim("username").asString();
            return "admin".equals(username);
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
