package com.study.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Locale locale, Model model) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = now.format(formatter);
        model.addAttribute("serverTime", formattedDate);

        return "home";
    }

    @GetMapping("/codeGroup")
    public String codeGroupHome(Locale locale, Model model) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = now.format(formatter);
        model.addAttribute("serverTime", formattedDate);

        return "codeGroup";
    }

    @GetMapping("/codeDetail")
    public String codeDetailHome(Locale locale, Model model) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = now.format(formatter);
        model.addAttribute("serverTime", formattedDate);

        return "codeDetail";
    }

    @GetMapping("/member")
    public String memberHome(Locale locale, Model model) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDate = now.format(formatter);
        model.addAttribute("serverTime", formattedDate);

        return "member";
    }
}
