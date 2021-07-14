package com.ws.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(){
        log.info("home -----------------------------------");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        log.info("login-----------------------------------");
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        log.info("login error-----------------------------------");
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }
}
