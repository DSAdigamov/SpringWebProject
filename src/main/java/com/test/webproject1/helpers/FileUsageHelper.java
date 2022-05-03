package com.test.webproject1.helpers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Component
public class FileUsageHelper {

    @Value("${imagePathToProfile}")
    private String uploadFolderPath;

    public void saveLocalPictureToImagesProfiles(Long user_id,MultipartFile multipartFile) throws IOException {
        Path path = Paths.get(uploadFolderPath);
        String fileNameWithId = user_id + "_" + multipartFile.getOriginalFilename();


        Files.copy(multipartFile.getInputStream(), path.resolve(fileNameWithId));
    }

    public void deleteLocalImageFromProfiles(String fileName){
        File file = new File(uploadFolderPath + fileName);
        file.delete();
    }

    public File getProfileImage(String imageName){
        File image = new File(uploadFolderPath + imageName);
        return image;
    }
}
