package com.test.webproject1.servises;

import com.test.webproject1.entities.Role;
import com.test.webproject1.entities.User;
import com.test.webproject1.repositories.RoleRepository;
import com.test.webproject1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            log.error("user not found in DB");
            throw new UsernameNotFoundException("User not found in D");
        } else {
            log.error("user found in DB: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public User registerUser(User user){
        User returnUser = saveUser(user);
        if (returnUser != null){
            addRoleToUser(user.getEmail(), "ROLE_USER");
            return returnUser;
        }
        else return null;
    }

    public User authenticate(String login, String password){
        return userRepository.findByEmailAndPassword(login, passwordEncoder.encode(password)).orElse(null);
    }

    public User saveUser(User user) {
        log.info("Saving new user");
        if (user.getEmail() != "" && user.getPassword() != "" && user.getName() != "" && userRepository.findByEmail(user.getEmail()) == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        log.info("wrong data");
        return null;
    }


    @Override
    public Role saveRole(Role role) {
        log.info("saving new Role: {}", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding role: {} to userEmail {} ", roleName, email);
        User user = userRepository.findByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String email) {
        log.info("getting user with email: {}", email );
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("getting all users");
        return userRepository.findAll();
    }

}
