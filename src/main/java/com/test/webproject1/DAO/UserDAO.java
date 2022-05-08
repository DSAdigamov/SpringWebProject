package com.test.webproject1.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class UserDAO {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private LocalDate dateOfRegistration;
    private String imagePath;
}
