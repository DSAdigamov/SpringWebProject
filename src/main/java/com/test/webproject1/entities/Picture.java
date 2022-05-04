package com.test.webproject1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id", nullable = false)
    private Integer id;

    @JoinColumn(name = "user_id")
    @OneToOne()
    private User user;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    @Column(name = "picture_name", length = 100)
    private String picture_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicture_name() {
        return picture_name;
    }

    public void setPicture_name(String path) {
        this.picture_name = path;
    }

}