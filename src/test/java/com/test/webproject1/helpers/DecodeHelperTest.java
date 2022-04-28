package com.test.webproject1.helpers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class DecodeHelperTest {

    @Autowired
    DecodeHelper decodeHelper;

    @Test
    public void getEmailFromAuthCookie(){
        assertEquals("test@gmail.com", decodeHelper.getEmailFromAuthCookie("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpc3MiOiIvbG9naW4iLCJleHAiOjE2NTEzMzg4NjZ9.MbY4Tg0mIdgXZGy6Gt_LugXbmWeatWWxZgIwrLb8RXs"));
    }
}