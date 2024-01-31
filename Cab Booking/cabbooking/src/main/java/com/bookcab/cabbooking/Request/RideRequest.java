package com.bookcab.cabbooking.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideRequest {
    
    private double pickupLongitude;
    private double pickupLatitude;
    private double destinationLongitude;
    private double destinationLatitude;
    private String pickupArea;
    private String destinationArea;
}
