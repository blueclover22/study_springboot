package com.study.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = now.format(formatter);
        model.addAttribute("serverTime", formattedDate);

        return "home";
    }
}
