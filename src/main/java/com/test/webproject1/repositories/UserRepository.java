package com.test.webproject1.repositories;

import com.test.webproject1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

}
