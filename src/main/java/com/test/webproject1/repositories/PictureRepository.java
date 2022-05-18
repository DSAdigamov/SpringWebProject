package com.test.webproject1.repositories;

import com.test.webproject1.entities.Picture;
import com.test.webproject1.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
    Picture findByUserEmail(String email);

    List<Picture> findAllByPostId(int id);

}
