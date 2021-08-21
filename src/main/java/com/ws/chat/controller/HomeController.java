package com.ws.chat.controller;

import com.ws.chat.dto.UserRequest;
import com.ws.chat.service.UserService;
import com.ws.chat.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping("/")
    public String home(){
        return "chat";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(Model model){
        model.addAttribute("userRequest", new UserRequest());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserRequest userRequest, BindingResult bindingResult){
        userValidator.validate(userRequest, bindingResult);
        if(bindingResult.hasErrors()){
            return "signup";
        }else {
            userService.save(userRequest);
            return "redirect:/";
        }
    }
}
