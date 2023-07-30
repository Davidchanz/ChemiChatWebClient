package com.chemichat.web.service;

import com.chemichat.web.dto.MessageDto;
import com.chemichat.web.dto.RoomDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class RoomService {
    public List<RoomDto> getAllRooms(String username, String token){
        //TODO
        //RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/rooms/all?username={username}";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", "Bearer " + token);
        Map<String, Object> map= new HashMap<>();
        map.put("username", username);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<RoomDto[]> response = restTemplate.exchange(
                fooResourceUrl, HttpMethod.GET, requestEntity, RoomDto[].class, map);
        System.out.println(response.getBody());//TODO chage RoomDto to normal

        return List.of(response.getBody());
    }

    public RoomDto getRoom(Long roomId, String token){
        //TODO
        //RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/room?roomId={roomId}";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", "Bearer " + token);
        Map<String, Object> map= new HashMap<>();
        map.put("roomId", roomId);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<RoomDto> response = restTemplate.exchange(
                fooResourceUrl, HttpMethod.GET, requestEntity, RoomDto.class, map);
        System.out.println(response.getBody());//TODO chage RoomDto to normal


        return response.getBody();
    }

    public List<MessageDto> getRoomHistory(Long roomId, String token){
        //TODO
        //RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/room/history?roomId={roomId}";
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set("Authorization", "Bearer " + token);
        Map<String, Object> map= new HashMap<>();
        map.put("roomId", roomId);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<MessageDto[]> response = restTemplate.exchange(
                fooResourceUrl, HttpMethod.GET, requestEntity, MessageDto[].class, map);
        System.out.println(response.getBody());//TODO chage RoomDto to normal
        if(response.getBody() == null)
            return new ArrayList<>();
        else
            return Arrays.stream(response.getBody()).toList();
    }
}
