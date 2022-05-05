package com.test.webproject1.controllers;

import ch.qos.logback.core.util.TimeUtil;
import com.test.webproject1.entities.Picture;
import com.test.webproject1.entities.User;
import com.test.webproject1.helpers.CookiesHelper;
import com.test.webproject1.helpers.FileUsageHelper;
import com.test.webproject1.servises.PictureService;
import com.test.webproject1.servises.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class    UserPageController {

    private UserServiceImpl userService;
    private CookiesHelper cookiesHelper;
    private FileUsageHelper fileUploadService;
    private PictureService pictureService;



    @GetMapping("/home")
    public String getUserPage(Model model, HttpServletRequest request){
        User user = userService.getUserWithRequest(request);
        model.addAttribute("imagePath", pictureService.getLoggedUserImagePathWithRequest(request));
        model.addAttribute("homePageRequest", user);
        return "/user/home";
    }

    @GetMapping("/home/change")
    public String getUserPageChange(Model model, HttpServletRequest request){
        User user = userService.getUserWithRequest(request);
        model.addAttribute("homeChangePageRequest", user);
        return "/user/homeChange";
    }

    @PostMapping("/home/change")
    public String changeUserData(@ModelAttribute User userModel, @RequestParam("image") MultipartFile multipartFile, HttpServletRequest request) throws IOException, InterruptedException {
        userService.UpdateUserNameAndPhone(request, userModel.getName(), userModel.getPhoneNumber());
        if (!multipartFile.isEmpty()){
            Optional<String> ext = Optional.ofNullable(multipartFile.getOriginalFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1));

            pictureService.saveUserPicture(request, userService.getUserWithRequest(request).getId() + "." + ext.get());
            fileUploadService.saveLocalPictureToImagesProfiles(userService.getUserWithRequest(request).getId() ,multipartFile);
        }
        return "redirect:/user/home";
    }

    @PostMapping("home/logout")
    public String userLogout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextHolder.clearContext();
        cookiesHelper.DeleteAuthCookies(response);
        return "redirect:/login";
    }



}
