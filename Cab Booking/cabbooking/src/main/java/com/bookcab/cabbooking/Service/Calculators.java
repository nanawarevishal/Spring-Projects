package com.bookcab.cabbooking.Service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;


@Service
public class Calculators {

    static final int EARTH_RADIUS=6371;
    
    public double calculateDistance(double sourceLatitude,double sourceLongitude,double destinationLatitude,double destinationLongitude){
        
        double dLat = Math.toRadians(destinationLatitude-sourceLatitude);
        double dlang = Math.toRadians(destinationLongitude-sourceLongitude);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                    + Math.cos((Math.toRadians(sourceLatitude)) * Math.cos(Math.toRadians(destinationLatitude)))
                    + Math.sin(dlang/2) * Math.sin(dlang/2);

        double c = 2* Math.atan2(Math.sqrt(a),Math.sqrt(1-a));

        double distance = EARTH_RADIUS * c;
        

        return distance;
    }


    public long calculateDuration(LocalDateTime startTime,LocalDateTime endTime){

        Duration duration = Duration.between(startTime, endTime);

        return duration.getSeconds();
    }

    public double getFare(double distance){
        double baseFare = 11;
        double totalFare = baseFare * distance;

        return totalFare;
    }


}
