package com.test.webproject1.controllers;

import com.test.webproject1.servises.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserPageController {

    private UserServiceImpl userService;


    @GetMapping("/home")
    public String getUserPage(Principal principal, Model model){
        System.out.println(principal.toString());
        return "mainPage/person";
    }
}
