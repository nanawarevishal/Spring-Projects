package com.practiseservices.servicespractise.Model;

import java.time.LocalDateTime;
import java.util.*;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String caption;

    private String imageUrl;

    private String videoUrl;

    @ManyToOne
    private User user;

    private LocalDateTime createdAt;

    // @OneToMany
    // private List<User>likedPost = new ArrayList<>();

    // @ManyToMany(mappedBy = "likedPosts")
    // private List<User> likedByUsers = new ArrayList<>();

    // @ManyToMany(mappedBy = "savedPosts")
    // private List<User> savedByUsers = new ArrayList<>();

    @ManyToMany(mappedBy = "likedPosts")
    private List<User> likedByUsers = new ArrayList<>();

    @ManyToMany(mappedBy = "savedPosts")
    private List<User> savedByUsers = new ArrayList<>();

}
