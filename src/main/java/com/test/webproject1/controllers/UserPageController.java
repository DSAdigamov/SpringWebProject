package com.test.webproject1.controllers;

import com.test.webproject1.entities.User;
import com.test.webproject1.servises.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserPageController {

    private UserServiceImpl userService;


    @GetMapping("/home")
    public String getUserPage(Model model, HttpServletRequest request){
        User user = userService.getUserWithCookie(request);
        model.addAttribute("homePageRequest", user);
        return "/user/home";
    }

    @GetMapping("/home/change")
    public String getUserPageChange(Model model, HttpServletRequest request){
        User user = userService.getUserWithCookie(request);
        model.addAttribute("homeChangePageRequest", user);
        return "/user/homeChange";
    }

    @PostMapping("/home/change")
    public String changeUserData(@ModelAttribute User userModel, HttpServletRequest request){
        System.out.println(userModel.toString() + "//////////////////////////////////////");
        userService.UpdateUserNameAndPhone(request, userModel.getName(), userModel.getPhoneNumber());
        return "redirect:/user/home";
    }

}
