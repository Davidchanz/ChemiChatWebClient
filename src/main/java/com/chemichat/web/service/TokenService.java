package com.chemichat.web.service;

import com.chemichat.web.dto.TokenDto;
import com.chemichat.web.dto.UserDto;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {
    public String getTokenRequest(String username, String password){
        //TODO
        //RESTTemplate todo
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/api/auth/login";
        HttpEntity<UserDto> request = new HttpEntity<>(new UserDto(username, password));
        var token = restTemplate.postForObject(fooResourceUrl, request, TokenDto.class);
        System.out.println(token.getToken());
        return token.getToken();
    }
}
