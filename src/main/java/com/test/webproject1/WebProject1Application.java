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

        //userService.saveUser(new User(null, "111@mail.ru", "петя", "1234"));
        userService.saveUser(new User(null, "222@mail.ru", "Боб>", "2341"));
        userService.saveUser(new User(null, "333@mail.ru", "Ваня", "3412"));
        userService.saveUser(new User(null, "444@mail.ru", "Вова", "4123"));

//        userService.addRoleToUser("111@mail.ru", "ROLE_USER");
//        userService.addRoleToUser("111@mail.ru", "ROLE_ADMIN");
//        userService.addRoleToUser("222@mail.ru", "ROLE_USER");
//        userService.addRoleToUser("444@mail.ru", "ROLE_USER");
//

        };
    }
}
