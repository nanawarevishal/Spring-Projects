package com.bookcab.cabbooking.Request;

import com.bookcab.cabbooking.Model.License;
import com.bookcab.cabbooking.Model.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverSignUpRequest {
    
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private double latitude;
    private double longitude;

    private License license;

    private Vehicle vehicle;

}
