package com.chemichat.web.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    @GetMapping("/getToken")
    public String getToken(@CookieValue(value = "token") String token){
        return token;
    }
}
