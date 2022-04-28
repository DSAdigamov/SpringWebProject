package com.test.webproject1.servises;

import com.test.webproject1.entities.Role;
import com.test.webproject1.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    User getUser(String email);
    List<User> getUsers();
//    void UpdateUserNameAndPhone(HttpServletRequest request, String name, String phone);
//    public User getUserWithCookie(HttpServletRequest request);
}
