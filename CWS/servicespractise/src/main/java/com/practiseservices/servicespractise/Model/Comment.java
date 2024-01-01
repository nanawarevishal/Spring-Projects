package com.practiseservices.servicespractise.Model;

import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

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


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JsonIgnore
    private Reel reel;

    @ManyToMany
    private List<User>liked = new ArrayList<>();

    private LocalDateTime createdAt;

}
