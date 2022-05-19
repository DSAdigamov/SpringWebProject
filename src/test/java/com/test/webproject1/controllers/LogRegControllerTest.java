package com.test.webproject1.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@AutoConfigureMockMvc
@SpringBootTest
class LogRegControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LogRegController logRegController;

    @Test
    public void badLoginRequest() throws Exception {
        this.mockMvc.perform(post("/login")
                        .param("email", "111@mail.ru").param("password", "wrong"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void CorrectLoginRequest() throws Exception {
        this.mockMvc.perform(post("/login")
                .param("email", "111@mail.ru").param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/home"));
    }


}