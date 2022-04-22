package com.test.webproject1.entities;

import javax.persistence.*;

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

    @Column(name = "path", length = 100)
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}