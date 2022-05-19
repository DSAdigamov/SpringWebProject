package com.test.webproject1.servises;

import com.test.webproject1.DTO.UserDTO;
import com.test.webproject1.entities.User;
import com.test.webproject1.repositories.PictureRepository;
import com.test.webproject1.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PictureRepository pictureRepository;


    @Test
    void getAllUsersDAO() {
        //given
        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User(1L, "email", "password", "name", "phoneNumber", LocalDate.now(), false));

        Mockito.when(userRepository.findAll()).thenReturn(userList);
        Mockito.when(pictureRepository.findByUserEmail("email")).thenReturn(null);

        //when
        ArrayList<UserDTO> userDTOList = userService.getAllUsersDAO();

        //then
        assertAll(
                () -> {
                    assertEquals(userDTOList.get(0).getId(), 1L);
                    assertEquals(userDTOList.get(0).getEmail(), "email");
                    assertEquals(userDTOList.get(0).getImagePath(), "/nullUserImage.png");
                }
                );
    }

}