package com.practiseservices.servicespractise.Model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String address;

    private String gender;

    private HashSet<Long> followers = new HashSet<>();

    private HashSet<Long> followings = new HashSet<>();

    // @ManyToMany
    // private List<Post>savedPosts = new ArrayList<>();

    // @ManyToMany
    // private List<Post> likedPosts = new ArrayList<>();

    // @ManyToMany
    // private List<Post> savedPosts = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "user_liked_posts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> likedPosts = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "user_saved_posts",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "post_id"))
    private List<Post> savedPosts = new ArrayList<>();


    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "user_liked_reels",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "reel_id"))
    private List<Reel> likedReels = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(
        name = "user_saved_reels",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "reel_id"))
    private List<Reel> savedReels = new ArrayList<>();
}
