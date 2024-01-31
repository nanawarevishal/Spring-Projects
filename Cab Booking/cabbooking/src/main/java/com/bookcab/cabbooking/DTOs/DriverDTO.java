package com.bookcab.cabbooking.DTOs;

import com.bookcab.cabbooking.Domain.UserRole;
import com.bookcab.cabbooking.Model.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private double rating;
    private double latitude;
    private double longitude;
    private UserRole role;
    private Vehicle vehicle;
      
}
