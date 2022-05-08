package com.test.webproject1.servises;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.test.webproject1.DAO.UserDAO;
import com.test.webproject1.helpers.CookiesHelper;
import com.test.webproject1.entities.Role;
import com.test.webproject1.entities.User;
import com.test.webproject1.helpers.DecodeHelper;
import com.test.webproject1.helpers.FileUsageHelper;
import com.test.webproject1.repositories.PictureRepository;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PictureService pictureService;

    private final PasswordEncoder passwordEncoder;
    private final CookiesHelper cookiesHelper;
    private final DecodeHelper decodeHelper;




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
        if (!user.getEmail().equals("") && !user.getPassword().equals("") && !user.getName().equals("") && userRepository.findByEmail(user.getEmail()) == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setDateOfRegistration(LocalDate.now());
            user.setActive(false);
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

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public List<User> getUsers() {
        log.info("getting all users");
        return userRepository.findAll();
    }

    public List<UserDAO> getAllUsersDAO() {
        log.info("getting all users");
        List<User> users = userRepository.findAll();
        List<UserDAO> userDAOList = null;
        for (int i = 0; i < users.size(); i++) {
            userDAOList.add(new UserDAO(users.get(i).getId(), users.get(i).getEmail(), users.get(i).getName(),
                    users.get(i).getPhoneNumber(), users.get(i).getDateOfRegistration(), pictureService.getUserImagePathById(users.get(i).getId())));
        }
        return userDAOList;
    }

    public User getUserWithRequest(HttpServletRequest request){
        Cookie authCookie = cookiesHelper.getAuthCookie(request);
        if (authCookie != null){
            String token = authCookie.getValue();
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String email = decodedJWT.getSubject();
            return userRepository.findByEmail(email);
        } else
            return null;
    }

    public void UpdateUserNameAndPhone(HttpServletRequest request,String name, String phone){
        Cookie authCookie = cookiesHelper.getAuthCookie(request);
        String userEmail = decodeHelper.getEmailFromAuthCookie(authCookie.getValue());

        if (!name.equals("")){
            userRepository.setUserName(userEmail, name);
        }

        if (!phone.equals("")){
            userRepository.setUserPhone(userEmail, phone);
        }
    }

    public void deleteUserByEmail(String email){
        userRepository.deleteUserByEmail(email);
    }




}
