package com.test.webproject1.repositories;

import com.test.webproject1.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUserId(Long id);

    @Modifying
    @Query("update Post p set p.postName = :postName where p.id = :postId")
    void setPostName(@Param("postId") int postId,@Param("postName") String postName);

    @Modifying
    @Query("update Post p set p.description = :description where p.id = :postId")
    void setPostDescription(@Param("postId") int postId,@Param("description") String description);

//    @Modifying
//    @Query("update Post p set p.postName = :postName, p.description = :description where p.id = :postId")
//    void setPostInfo(@Param("postId") int postId,@Param("postName") String postName, @Param("description") String description);
}
