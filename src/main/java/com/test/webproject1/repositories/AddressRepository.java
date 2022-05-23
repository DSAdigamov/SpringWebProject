package com.test.webproject1.repositories;

import com.test.webproject1.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Modifying
    @Query("update Address a set a.city = :city where a.post.id = :postId")
    void setCityById(@Param("postId") int postId, @Param("city") String city);

    @Modifying
    @Query("update Address a set a.fullAddress = :fullAddress where a.post.id = :postId")
    void setFullAddressById(@Param("postId") int postId,@Param("fullAddress") String fullAddress);
}
