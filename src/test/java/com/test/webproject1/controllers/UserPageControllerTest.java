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

import javax.servlet.http.Cookie;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPageController userPageController;

    @Disabled
    @Test
    public void userPageTest() throws Exception {
        Cookie cookie = new Cookie("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTFAbWFpbC5ydSIsInJvbGVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiaXNzIjoiL2xvZ2luIiwiZXhwIjoxNjUzMTYxNDA1fQ.FrvzA8yoGfoJoelQakoa1h1_M_BNNVQbO5vvDSjoeIQ");
        this.mockMvc.perform(get("/user/home").cookie(cookie))
                .andExpect(status().isOk());
                //.andExpect(MockMvcResultMatchers.xpath("//*[@id=\"sidebar\"]/div[2]/div/h2").string("петя"));
    }

}