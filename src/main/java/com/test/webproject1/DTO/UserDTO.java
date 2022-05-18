package com.test.webproject1.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private LocalDate dateOfRegistration;
    private String imagePath;
}
