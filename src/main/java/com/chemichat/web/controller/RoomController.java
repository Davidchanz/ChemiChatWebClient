package com.chemichat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RoomController {

    @GetMapping("/room")
    public ModelAndView openRoom(@RequestParam("roomId") Long roomId, ModelMap model){
        model.addAttribute("roomId", roomId);
        return new ModelAndView("redirect:/", model);
    }

}
