package com.chemichat.web.controller;

import com.chemichat.web.dto.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(Model model, HttpServletResponse response){
        model.addAttribute("user", new UserDto());
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0); // delete cookie
        response.addCookie(cookie);
        return "login";
    }
}
