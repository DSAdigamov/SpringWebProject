package com.test.webproject1.controllers;


import com.test.webproject1.DTO.PostDTO;
import com.test.webproject1.DTO.PostDTOForAllView;
import com.test.webproject1.entities.Post;
import com.test.webproject1.entities.User;
import com.test.webproject1.repositories.PictureRepository;
import com.test.webproject1.repositories.PostRepository;
import com.test.webproject1.servises.PictureService;
import com.test.webproject1.servises.PostService;
import com.test.webproject1.servises.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RequestMapping("/post")
@Controller
public class PostController {


    private final PictureService pictureService;
    private final UserServiceImpl userService;
    private final PostService postService;

    private final PostRepository postRepository;

    @GetMapping("/new")
    public String getPostCreatePage(HttpServletRequest request, Model model){
        User user = userService.getUserWithRequest(request);
        model.addAttribute("imageSidebarPath", pictureService.getLoggedUserImagePathWithRequestForSidebar(request));
        model.addAttribute("LoggedUser", user);

        model.addAttribute("newPostDAO", new PostDTO());
        return "/post/postCreatePage";
    }

    @PostMapping("/new")
    public String createNewPostPage(@ModelAttribute PostDTO postData, HttpServletRequest request) throws IOException {
        if (postData.getMainImage().isEmpty() || postData.getPostName().equals("") ||
                postData.getCity().equals("")  || postData.getFullAddress().equals("") || postData.getDescription().equals("")){
            return "redirect:/post/new";
        } else
        {
            Post newPost = postService.saveNewPost(postData, userService.getUserWithRequest(request));
            return "redirect:/post/" + newPost.getId() + "/imagesChange";
        }
    }

    @GetMapping("/{id}/imagesChange")
    public String getPostChangeImagesPage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        User user = userService.getUserWithRequest(request);
        model.addAttribute("imageSidebarPath", pictureService.getLoggedUserImagePathWithRequestForSidebar(request));
        model.addAttribute("LoggedUser", user);

        model.addAttribute("postAction", "/post/" + id + "/imagesChange");
        model.addAttribute("redirectToPost", "/post/" + id);

        if (!Objects.equals(postService.getPostCreatorId(Integer.parseInt(id)), user.getId())){
            response.sendError(403);
            return "redirect:/error";
        }

        model.addAttribute("imagesList", pictureService.getPostPictures(Integer.parseInt(id)));
        return "/post/postImagesChange";
    }

    @PostMapping("/{id}/imagesChange")
    public String getUploadImageForPostPage(@PathVariable String id,@RequestParam("image") MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()){
            return "redirect:/post/" + id + "/imagesChange";
        } else {
            pictureService.savePostPicture(postRepository.getById(Integer.parseInt(id)), imageFile);
            return "redirect:/post/" + id + "/imagesChange";
        }

    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable String id, HttpServletRequest request, Model model){
        User loggedUser = userService.getUserWithRequest(request);
        model.addAttribute("imageSidebarPath", pictureService.getLoggedUserImagePathWithRequestForSidebar(request));
        model.addAttribute("LoggedUser", loggedUser);

        Post post =postRepository.getById(Integer.parseInt(id));
        model.addAttribute("imagesList", pictureService.getPostPictures(Integer.parseInt(id)));
        model.addAttribute("postCreatorUserImage", pictureService.getUserImagePathById(post.getUser().getId()));
        model.addAttribute("postData", post);

        if (!post.getUser().getId().equals(loggedUser.getId())){
            model.addAttribute("isUserCreator", "visibility: hidden");
        }
        else model.addAttribute("isUserCreator", "visibility: visible");


        return "/post/postPage";
    }

    @GetMapping("/{id}/infoChange")
    public String getUpdateInfoPage(@PathVariable String id, HttpServletRequest request, HttpServletResponse response ,Model model) throws IOException {
        User loggedUser = userService.getUserWithRequest(request);
        model.addAttribute("imageSidebarPath", pictureService.getLoggedUserImagePathWithRequestForSidebar(request));
        model.addAttribute("LoggedUser", loggedUser);

        if (!Objects.equals(postService.getPostCreatorId(Integer.parseInt(id)), loggedUser.getId())){
            response.sendError(403);
            return "redirect:/error";
        }

        Post post =postRepository.getById(Integer.parseInt(id));
        model.addAttribute("postData", post);
        model.addAttribute("PostDAO", new PostDTO());

        return "/post/postChangeInfoPage";
    }

    @PostMapping("/{id}/infoChange")
    public String updatePostInfo(@ModelAttribute PostDTO postData, @PathVariable String id) throws IOException {
        System.out.println(postData.toString());
        postService.changePostInfoById(Integer.parseInt(id), postData.getPostName(), postData.getCity(), postData.getFullAddress(), postData.getDescription());
        return "redirect:/post/" + id;
    }



    @GetMapping("/getAll")
    public String getAllPosts(HttpServletRequest request, Model model){
        User user = userService.getUserWithRequest(request);
        model.addAttribute("imageSidebarPath", pictureService.getLoggedUserImagePathWithRequestForSidebar(request));
        model.addAttribute("LoggedUser", user);

        model.addAttribute("PostsList", postService.getAllPosts());

        return "/post/postGetAll";
    }


}
