package com.test.webproject1.servises;

import com.test.webproject1.DTO.PictureDTO;
import com.test.webproject1.DTO.PostDTO;
import com.test.webproject1.entities.Picture;
import com.test.webproject1.entities.Post;
import com.test.webproject1.entities.User;
import com.test.webproject1.helpers.FileUsageHelper;
import com.test.webproject1.repositories.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PictureService {

    private final UserServiceImpl userService;

    private final PictureRepository pictureRepository;

    private final FileUsageHelper fileUsageHelper;


    public void saveUserPicture(HttpServletRequest request, String fileName){
        User user = userService.getUserWithRequest(request);

        Picture pic = new Picture();
        pic.setUser(user);
        pic.setPicture_name(fileName);

        Picture oldPicture = pictureRepository.findByUserEmail(user.getEmail());
        if (oldPicture != null){
            fileUsageHelper.deleteLocalImageFromProfiles(oldPicture.getPicture_name());
            pictureRepository.delete(oldPicture);
        }
        pictureRepository.save(pic);
    }

    public void savePostPicture(Post post , MultipartFile image) throws IOException {

        Optional<String> ext = Optional.ofNullable(image.getOriginalFilename())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(image.getOriginalFilename().lastIndexOf(".") + 1));

        String fileName =post.getUser().getId() + "_" + (pictureRepository.count()+1) + "." + ext.get();
        Picture pic = new Picture(post, fileName);
        pictureRepository.save(pic);
        fileUsageHelper.saveLocalPictureToImagesPosts(image, fileName, ext.get());
    }

    public String getLoggedUserImagePathWithRequest(HttpServletRequest request){
        Picture userProfilePicture = pictureRepository.findByUserEmail(userService.getUserWithRequest(request).getEmail());

        if (userProfilePicture != null){
            return "../../../../images/profiles/" + userProfilePicture.getPicture_name();
        } else
            return "/nullUserImage.png";
    }

    public String getLoggedUserImagePathWithRequestForSidebar(HttpServletRequest request){
        Picture userProfilePicture = pictureRepository.findByUserEmail(userService.getUserWithRequest(request).getEmail());
        if (userProfilePicture != null){
            return "/images/profiles/" + userProfilePicture.getPicture_name();
        } else
            return "/nullUserImage.png";
    }

    public String getUserImagePathById(Long id){
        Picture userProfilePicture = pictureRepository.findByUserEmail(userService.getUserById(id).get().getEmail());

        if (userProfilePicture != null){
            return "../../../../images/profiles/" + userProfilePicture.getPicture_name();
        } else
            return "/nullUserImage.png";
    }

    public ArrayList<PictureDTO> getPostPictures(int id){
        List<Picture> images = pictureRepository.findAllByPostId(id);
        ArrayList<PictureDTO> newImagesDTO = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            newImagesDTO.add(new PictureDTO(images.get(i).getId(),images.get(i).getPost(),
                    images.get(i).getPicture_name(), "../../../../images/posts/" + images.get(i).getPicture_name()));
        }
        return newImagesDTO;
    }

}
