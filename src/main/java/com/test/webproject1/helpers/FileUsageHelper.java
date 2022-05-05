package com.test.webproject1.helpers;

import lombok.Data;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Data
@Component
public class FileUsageHelper {

    @Value("${imagePathToProfile}")
    private String uploadFolderPath;

    public void saveLocalPictureToImagesProfiles(Long user_id,MultipartFile multipartFile) throws IOException {
        String filename = multipartFile.getOriginalFilename();

        BufferedImage croppedImage = cropImageMax240To320(multipartFile);
        Optional<String> ext = Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));

        String newFileNameWithId = user_id + "." + ext.get() ;
        String fullPath = uploadFolderPath + newFileNameWithId;


        File outputFile = new File(fullPath);
        ImageIO.write(croppedImage, ext.get(), outputFile);
        //Files.copy(multipartFile.getInputStream(), path.resolve(fileNameWithId));
    }

    public void deleteLocalImageFromProfiles(String fileName){
        File file = new File(uploadFolderPath + fileName);
        file.delete();
    }

    public BufferedImage cropImageMax240To320(MultipartFile imageFile) throws IOException {

        InputStream in = new ByteArrayInputStream(imageFile.getBytes());
        BufferedImage originalImage = ImageIO.read(in);



        int height = originalImage.getHeight();
        int width = originalImage.getWidth();



        if (height <= 320 && width <= 240){
            return originalImage;
        } else {
            if (height > 320 && width <=240){
                int centeredUpperLeftCornerYThenWidthNormal = (height - 320)/2;
                BufferedImage croppedImage = originalImage.getSubimage(0,
                        centeredUpperLeftCornerYThenWidthNormal, width, 320);
                return croppedImage;
            }

            if (height <=320 && width > 240){
                int centeredUpperLeftCornerXThenHeightNormal = (width - 240)/2;
                BufferedImage croppedImage = originalImage.getSubimage(centeredUpperLeftCornerXThenHeightNormal,
                        0, 240, height);
                return croppedImage;
            }

            int centeredUpperLeftCornerX = (width - 240)/2;
            int centeredUpperLeftCornerY = (height - 320)/2;
            BufferedImage croppedImage = originalImage.getSubimage(centeredUpperLeftCornerX,
                    centeredUpperLeftCornerY, 240, 320);

            return croppedImage;
        }
    }
}
