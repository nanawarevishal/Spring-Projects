package com.practiseservices.servicespractise.Model;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Reel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String caption;

    private String videoUrl;

    @ManyToOne
    private User user;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "reel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment>comments = new ArrayList<>();

    @ManyToMany(mappedBy = "likedReels")
    private List<User> likedByUsers = new ArrayList<>();

    @ManyToMany(mappedBy = "savedReels")
    private List<User> savedByUsers = new ArrayList<>();
}