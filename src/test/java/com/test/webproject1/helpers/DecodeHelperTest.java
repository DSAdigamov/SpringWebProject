package com.test.webproject1.helpers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecodeHelperTest {

    private static DecodeHelper decodeHelper;

    @BeforeAll
    public static void setUp(){
        decodeHelper = new DecodeHelper();
    }

    @Test
    void getEmailFromAuthCookie() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTFAbWFpbC5ydSIsInJvbGVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiaXNzIjoiL2xvZ2luIiwiZXhwIjoxNjUzMDY4MTIyfQ.nUcsVz25C8o6FIZVJgSHKNKqoYwZh7qeWair-3Ij8MI";
        String email = decodeHelper.getEmailFromAuthCookieToken(token);

        assertEquals("111@mail.ru", email);
    }

    @Test
    void getRolesFromAuthCookie() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTFAbWFpbC5ydSIsInJvbGVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiaXNzIjoiL2xvZ2luIiwiZXhwIjoxNjUzMDY4MTIyfQ.nUcsVz25C8o6FIZVJgSHKNKqoYwZh7qeWair-3Ij8MI";
        String[] roles = decodeHelper.getRolesFromAuthCookieToken(token);

        assertAll(
                () ->{
                    assertEquals(roles[0], "ROLE_ADMIN");
                    assertEquals(roles[1], "ROLE_USER");
                }
        );
    }
}