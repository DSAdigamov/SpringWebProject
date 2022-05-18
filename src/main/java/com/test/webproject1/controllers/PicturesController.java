package com.test.webproject1.controllers;

import com.test.webproject1.entities.Picture;
import com.test.webproject1.entities.User;
import com.test.webproject1.helpers.FileUsageHelper;
import com.test.webproject1.repositories.PictureRepository;
import com.test.webproject1.servises.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/images")
@Controller
public class PicturesController {

    private final UserServiceImpl userService;
    private final PictureRepository pictureRepository;

    private final FileUsageHelper fileUsageHelper;

    @RequestMapping("/delete/{id}")
    public String deleteImageById(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User loggedUser = userService.getUserWithRequest(request);
        Picture pic = pictureRepository.getById(Integer.parseInt(id));
        String postId = pic.getPost().getId().toString();

        if (!loggedUser.getId().equals(pic.getPost().getUser().getId())){
            response.sendError(403);
            return "redirect:/error";

        } else {
            fileUsageHelper.deleteLocalImageFromPosts(pic.getPicture_name());
            pictureRepository.deleteById(Integer.parseInt(id));
            return "redirect:/post/" + postId + "/imagesChange";
        }
    }
}
