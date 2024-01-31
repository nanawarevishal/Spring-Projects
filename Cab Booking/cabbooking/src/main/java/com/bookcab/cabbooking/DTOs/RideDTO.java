package com.bookcab.cabbooking.DTOs;

import java.time.LocalDateTime;

import com.bookcab.cabbooking.Domain.RideStatus;
import com.bookcab.cabbooking.Model.PaymentDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideDTO {
    
    private Long id;
    private UserDTO user;
    private DriverDTO driver;
    private double pickUplatitude;
    private double pickUpLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
    private String pickupArea;
    private String destinationArea;
    private double distance;
    private long duration;
    private RideStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double fare;
    private PaymentDetails paymentDetails;
    private int otp;

}
