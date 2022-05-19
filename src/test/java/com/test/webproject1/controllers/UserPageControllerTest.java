package com.test.webproject1.controllers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@SpringBootTest
@WithUserDetails("111@mail.ru")
class UserPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPageController userPageController;

    @Disabled
    @Test
    public void userPageTest() throws Exception {
        this.mockMvc.perform(get("/user/home"))
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("//*[@id=\"sidebar\"]/div[2]/div/h2").string("петя"));
    }

}