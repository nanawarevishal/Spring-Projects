package com.bookcab.cabbooking.Service;

import com.bookcab.cabbooking.Model.Driver;
import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Model.User;
import com.bookcab.cabbooking.Request.RideRequest;

public interface RideService {
    
    public Ride requestRide(RideRequest request,User user);

    public Ride createRide(User user,Driver nearestDriver,double sourceLatitude,double sourceLongitude,double destinationLatitude,double destinationLongitude,String pickUpArea,String destinationArea);

    public void acceptRide(Long rideId);

    public void declinedRide(Long rideId,Long driverId);

    public void startRide(Long rideId,int otp);

    public void completeRide(Long rideId);

    public void cancelRide(Long rideId);

    public Ride findRideById(Long rideId);


}
