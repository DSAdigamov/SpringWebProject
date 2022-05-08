package com.test.webproject1.controllers;

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
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Controller
@RequestMapping("/user")
public class UserPageController {

    private UserServiceImpl userService;
    private FileUsageHelper fileUploadService;
    private PictureService pictureService;

    private CookiesHelper cookiesHelper;

    @GetMapping("getAll")
    public String getAllUsersPage(Model model, HttpServletRequest request){
        User user = userService.getUserWithRequest(request);
        model.addAttribute("imageSidebarPath", pictureService.getLoggedUserImagePathWithRequestForSidebar(request));
        model.addAttribute("LoggedUser", user);
        //model.addAttribute("allUsersDAOList", userService.getAllUsersDAO());
        return "/user/allUsers";
    }


    @GetMapping("/{id}")
    public String getUserPageById(@PathVariable String id, Model model, HttpServletRequest request){
        Optional<User> searchedUser = userService.getUserById(Long.parseLong(id));
        System.out.println(searchedUser);
        if (searchedUser.isEmpty()){
            model.addAttribute("errorCode", "404");
            model.addAttribute("errorMsg", "Http Error Code: 404. Resource not found");
            return "/error";

        } else {
            User user = userService.getUserWithRequest(request);
            model.addAttribute("imageSidebarPath", pictureService.getLoggedUserImagePathWithRequestForSidebar(request));
            model.addAttribute("LoggedUser", user);
            model.addAttribute("searchedUser", searchedUser.get());
            model.addAttribute("imagePath", pictureService.getUserImagePathById(Long.parseLong(id)));
            return "/user/userPage";
        }
    }

    @GetMapping("/home")
    public String getUserPageHome(Model model, HttpServletRequest request){
        if (cookiesHelper.getAuthCookie(request) == null) {
            return "redirect:/login";
        } else {
            User user = userService.getUserWithRequest(request);
            model.addAttribute("imageSidebarPath", pictureService.getLoggedUserImagePathWithRequestForSidebar(request));
            model.addAttribute("imagePath", pictureService.getLoggedUserImagePathWithRequest(request));
            model.addAttribute("LoggedUser", user);
            return "/user/home";
        }
    }

    @GetMapping("/home/change")
    public String getUserPageChange(Model model, HttpServletRequest request){
        User user = userService.getUserWithRequest(request);
        model.addAttribute("imageSidebarPath", pictureService.getLoggedUserImagePathWithRequestForSidebar(request));
        model.addAttribute("LoggedUser", user);
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

    @PostMapping("/logout")
    public String userLogout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextHolder.clearContext();
        cookiesHelper.DeleteAuthCookies(response);
        return "redirect:/login";
    }



}
