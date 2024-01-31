package com.bookcab.cabbooking.Model;

import java.util.*;

import com.bookcab.cabbooking.Domain.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private double rating;

    private double latitude;
    private double longitude;

    private UserRole role;

    private String password;

    @JsonIgnore
    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
    private License license;

    @JsonIgnore
    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Ride>rides;

    @JsonIgnore
    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL,orphanRemoval = true)
    private Vehicle vehicle;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Ride currentRide;

    private Integer totalRevenue = 0;

}
