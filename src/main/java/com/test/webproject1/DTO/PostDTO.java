package com.test.webproject1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDTO {
    private MultipartFile mainImage;
    private String postName;
    private String city;
    private String fullAddress;
    private String description;
}
