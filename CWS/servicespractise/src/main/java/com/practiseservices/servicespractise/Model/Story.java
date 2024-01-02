package com.practiseservices.servicespractise.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Story {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String caption;

    @ManyToOne
    private User user;

    private String videoUrl;

    @OneToMany
    private List<Comment>comments = new ArrayList<>();

    @ManyToMany
    private List<User>likedByUser = new ArrayList<>();

    @ManyToMany
    private List<User>savedByUser = new ArrayList<>();

    private LocalDateTime expirationLocalTime;

    private LocalDateTime createdAt;

}
