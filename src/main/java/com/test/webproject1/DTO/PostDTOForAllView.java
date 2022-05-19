package com.test.webproject1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDTOForAllView {

    private int Id;
    private String postName;
    private String city;
    private LocalDate dateOfPost;
    private String mainImagePath;
}
