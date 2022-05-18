package com.test.webproject1.helpers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;

@Data
@Component
public class FileUsageHelper {

    @Value("${imagePathToProfile}")
    private String uploadProfileImagesFolderPath;

    @Value("${imagePathToPost}")
    private String uploadPostImagesFolderPath;

    public void saveLocalPictureToImagesProfiles(Long user_id,MultipartFile multipartFile) throws IOException {
        String filename = multipartFile.getOriginalFilename();

        BufferedImage croppedImage = cropImage(multipartFile, 240, 320);
        Optional<String> ext = Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));

        String newFileNameWithId = user_id + "." + ext.get() ;
        String fullPath = uploadProfileImagesFolderPath + newFileNameWithId;


        File outputFile = new File(fullPath);
        ImageIO.write(croppedImage, ext.get(), outputFile);
        //Files.copy(multipartFile.getInputStream(), path.resolve(fileNameWithId));
    }

    public void saveLocalPictureToImagesPosts(MultipartFile multipartFile, String fileName, String ext) throws IOException {
        BufferedImage croppedImage = cropImage(multipartFile, 640, 480);

        String fullPath = uploadPostImagesFolderPath + fileName;
        File outputFile = new File(fullPath);
        ImageIO.write(croppedImage, ext, outputFile);
    }

    public void deleteLocalImageFromProfiles(String fileName){
        File file = new File(uploadProfileImagesFolderPath + fileName);
        file.delete();
    }

    public void deleteLocalImageFromPosts(String filename){
        File file = new File(uploadPostImagesFolderPath + filename);
        file.delete();
    }



    public BufferedImage cropImage(MultipartFile imageFile, int neededWight, int neededHeight) throws IOException {

        InputStream in = new ByteArrayInputStream(imageFile.getBytes());
        BufferedImage originalImage = ImageIO.read(in);



        int height = originalImage.getHeight();
        int width = originalImage.getWidth();


        if (height <= neededHeight && width <= neededWight){
            return originalImage;
        } else {
            if (height > neededHeight && width <=neededWight){
                int centeredUpperLeftCornerYThenWidthNormal = (height - neededHeight)/2;
                BufferedImage croppedImage = originalImage.getSubimage(0,
                        centeredUpperLeftCornerYThenWidthNormal, width, neededHeight);
                return croppedImage;
            }

            if (height <=neededHeight && width > neededWight){
                int centeredUpperLeftCornerXThenHeightNormal = (width - neededWight)/2;
                BufferedImage croppedImage = originalImage.getSubimage(centeredUpperLeftCornerXThenHeightNormal,
                        0, neededWight, height);
                return croppedImage;
            }

            int centeredUpperLeftCornerX = (width - neededWight)/2;
            int centeredUpperLeftCornerY = (height - neededHeight)/2;
            BufferedImage croppedImage = originalImage.getSubimage(centeredUpperLeftCornerX,
                    centeredUpperLeftCornerY, neededWight, neededHeight);

            return croppedImage;
        }
    }
}
