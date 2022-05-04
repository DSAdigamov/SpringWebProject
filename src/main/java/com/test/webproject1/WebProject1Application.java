package com.test.webproject1;


import com.test.webproject1.entities.Role;
import com.test.webproject1.entities.User;
import com.test.webproject1.servises.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@SpringBootApplication
public class WebProject1Application {

    public static void main(String[] args)  {
        SpringApplication.run(WebProject1Application.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    CommandLineRunner run(UserService userService){
        return args ->{
            userService.saveRole(new Role(null, "ROLE_USER"));
        userService.saveRole(new Role(null, "ROLE_ADMIN"));
        userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

        userService.saveUser(new User(null, "111@mail.ru",  "1234", "петя"));
        userService.saveUser(new User(null, "222@mail.ru", "2341" , "Боб"));
        userService.saveUser(new User(null, "333@mail.ru", "3412" , "Ваня"));
        userService.saveUser(new User(null, "444@mail.ru", "4123" , "Вова"));

        userService.addRoleToUser("111@mail.ru", "ROLE_USER");
        userService.addRoleToUser("111@mail.ru", "ROLE_ADMIN");
        userService.addRoleToUser("222@mail.ru", "ROLE_USER");
        userService.addRoleToUser("444@mail.ru", "ROLE_USER");
//

        };
    }


}
