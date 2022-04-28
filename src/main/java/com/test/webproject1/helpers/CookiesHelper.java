package com.test.webproject1.helpers;


import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class CookiesHelper {

    public Cookie getAuthCookie(HttpServletRequest request){
        List<Cookie> authcookieList = stream(request.getCookies()).filter(x -> x.getName().equals("Authorization")).collect(Collectors.toList());
        if (authcookieList.size() != 0){
            return authcookieList.get(0);
        } else
            return null;
    }
}
