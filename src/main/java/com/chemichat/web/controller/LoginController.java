package com.chemichat.web.controller;

import com.chemichat.web.dto.UserDto;
import com.chemichat.web.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private TokenService tokenRequestService;

    @GetMapping("/login")
    public String showLoginPage(Model model){
        model.addAttribute("user", new UserDto());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserDto userDto, HttpServletResponse response){
        String token = tryLogin(userDto);
        if(token != null) {
            // set a new cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(24000);//TODO
            // add cookie in server response
            response.addCookie(cookie);
            return "redirect:/";
        }
        else
            return "login";
    }

    private String tryLogin(UserDto userDto) {
        return tokenRequestService.getTokenRequest(userDto.getUsername(), userDto.getPassword());
    }
}
