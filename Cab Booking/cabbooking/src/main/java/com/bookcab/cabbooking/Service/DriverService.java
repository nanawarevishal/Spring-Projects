package com.bookcab.cabbooking.Service;

import java.util.*;

import com.bookcab.cabbooking.Model.Driver;
import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Request.DriverSignUpRequest;

public interface DriverService {
    
    public Driver registerDriver(DriverSignUpRequest request);

    public List<Driver> getAvailableDrivers(double pickupLatitude,double pickupLongitude,Ride ride);

    public Driver findNearestDriver(List<Driver>availableDrivers,double pickupLatitude,double pickupLongitude);

    public Driver getRequestDriverProfile(String jwt);

    public Ride getDriverCurrentRide(Long driverId);

    public List<Ride> getAllocatedRide(Long driverId);

    public Driver findDriverById(Long driverId);

    public List<Ride> getCompletedRide(Long driverId);


}
