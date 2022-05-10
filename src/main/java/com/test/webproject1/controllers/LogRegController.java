package com.test.webproject1.controllers;

import com.test.webproject1.helpers.CookiesHelper;
import com.test.webproject1.entities.User;
import com.test.webproject1.servises.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@AllArgsConstructor
@Controller
public class LogRegController {

    private UserServiceImpl userService;

    private CookiesHelper cookiesHelper;

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpServletRequest request){
        if (cookiesHelper.getAuthCookie(request) != null){
            return "redirect:user/home";
        }else {
            model.addAttribute("loginRequest", new User());
            return "mainPage/login";
        }
    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute User user, Model model){
//        System.out.println("login request: " + user);
//        User checkedUser = userService.authenticate(user.getEmail(), user.getPassword());
//        if (checkedUser == null){
//            log.info("error//////////////////////");
//            return "redirect:/error_page";
//        }else {
//            model.addAttribute("userLogin", checkedUser.getEmail());
//            log.info("yes/////////////////////////////////////");
//            return "redirect:/user/home";
//        }
//    }

    @GetMapping("/register")
    public String getRegisterPage(Model model, HttpServletRequest request){
        if (cookiesHelper.getAuthCookie(request) != null) {
            return "redirect:user/home";
        } else {
            model.addAttribute("registerRequest", new User());
            return "mainPage/registration";
        }
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User userModel){
        System.out.println("register request: " + userModel);
        User registeredUser = userService.registerUser(userModel);
        if (registeredUser == null){
            return "redirect:/error_page";
        }else {
            return "redirect:/login";
        }
    }

}
