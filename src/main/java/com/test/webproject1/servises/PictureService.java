package com.test.webproject1.servises;

import com.test.webproject1.entities.Picture;
import com.test.webproject1.entities.User;
import com.test.webproject1.helpers.FileUsageHelper;
import com.test.webproject1.repositories.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

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
        pic.setPath(fileName);

        Picture oldPicture = pictureRepository.findByUserEmail(user.getEmail());
        if (oldPicture != null){
            fileUsageHelper.deleteLocalImageFromProfiles(oldPicture.getPath());
            pictureRepository.delete(oldPicture);
        }
        pictureRepository.save(pic);
    }

    public File getLoggedUserImageFile(HttpServletRequest request){
        Picture picture = pictureRepository.findByUserEmail(userService.getUserWithRequest(request).getEmail());
        File file = new File(fileUsageHelper.getUploadFolderPath() + picture.getPath());
        return file;
    }

}
