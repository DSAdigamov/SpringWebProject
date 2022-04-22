package com.test.webproject1.entities;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @JoinColumn(name = "post_id")
    @OneToOne
    private Post post;

    @Column(name = "city", length = 30)
    private String city;

    @Column(name = "\"fullAddress\"", length = 100)
    private String fullAddress;

    public Address() {
    }

    public Address(Post post, String city, String fullAddress) {
        this.post = post;
        this.city = city;
        this.fullAddress = fullAddress;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

}