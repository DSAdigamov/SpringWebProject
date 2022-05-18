package com.test.webproject1.DTO;

import com.test.webproject1.entities.Post;
import com.test.webproject1.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PictureDTO {
    private Integer id;
    private User user;
    private Post post;
    private  String picture_name;
    private String pictureFullPath;

    public PictureDTO(Integer id, Post post, String picture_name, String pictureFullPath) {
        this.id = id;
        this.post = post;
        this.picture_name = picture_name;
        this.pictureFullPath = pictureFullPath;
    }
}
