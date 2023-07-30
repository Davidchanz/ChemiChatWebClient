package com.chemichat.web.controller;

import com.chemichat.web.dto.MessageDto;
import com.chemichat.web.dto.UserDto;
import com.chemichat.web.service.MessageService;
import com.chemichat.web.service.RoomService;
import com.chemichat.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/")
    public String showChat(@RequestParam(value = "roomId", required = false) Long roomId, Model model, @CookieValue(value = "token") String token){
        var rooms = roomService.getAllRooms(userService.getUsername(token), token);
        model.addAttribute("rooms", rooms);
        model.addAttribute("message", new MessageDto());
        model.addAttribute("username", userService.getUsername(token));
        if(roomId != null){
            model.addAttribute("activeRoom", roomService.getRoom(roomId, token));
            model.addAttribute("history", roomService.getRoomHistory(roomId, token));
        }
        return "chat";
    }

    @PostMapping("/send")
    @ResponseBody
    public void sendMessage(@ModelAttribute("message") MessageDto messageDto, @CookieValue(value = "token") String token){
        System.out.println("send...");
        messageService.sendMessage(messageDto, token);
    }

}
