package com.test.webproject1.servises;

import com.test.webproject1.entities.Role;
import com.test.webproject1.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    User getUser(String email);
    List<User> getUsers();
}
