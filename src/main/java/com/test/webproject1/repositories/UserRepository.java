package com.test.webproject1.repositories;

import com.test.webproject1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    @Modifying
    @Query("update User u set u.name = :name where u.email= :email")
    void setUserName(@Param("email") String email, @Param("name") String name);

    @Modifying
    @Query("update User u set u.phoneNumber = :phone where u.email= :email")
    void setUserPhone(@Param("email") String email, @Param("phone") String phone);

    void deleteUserByEmail(String email);

}
