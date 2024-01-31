package com.bookcab.cabbooking.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;


import com.bookcab.cabbooking.Domain.RideStatus;
import com.bookcab.cabbooking.Exception.DriverException;
import com.bookcab.cabbooking.Exception.RideException;
import com.bookcab.cabbooking.Model.Driver;
import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Model.User;
import com.bookcab.cabbooking.Repository.DriverRepository;
import com.bookcab.cabbooking.Repository.RideRepository;
import com.bookcab.cabbooking.Request.RideRequest;



@Service
public class RideServiceImpl implements RideService {

    private DriverService driverService;

    private Calculators calculators;

    private RideRepository rideRepository;

    private DriverRepository driverRepository;

    public RideServiceImpl(DriverService driverService,Calculators calculators,RideRepository rideRepository,
    DriverRepository driverRepository){
        this.driverService = driverService;
        this.calculators = calculators;
        this.rideRepository = rideRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public Ride requestRide(RideRequest request, User user) {
       
        double pickupLatitude = request.getPickupLatitude();
        double pickupLongitude = request.getPickupLongitude();
        double destinationLatitude = request.getDestinationLatitude();
        double destinationLongitude = request.getDestinationLongitude();
        String pickUpArea = request.getPickupArea();
        String destinationArea = request.getDestinationArea();

        Ride existigRide = new Ride();

        List<Driver> availableDriver = driverService.getAvailableDrivers(pickupLatitude, pickupLongitude, existigRide);

        Driver nearestDriver = driverService.findNearestDriver(availableDriver, pickupLatitude, pickupLongitude);

        if(nearestDriver == null){
            throw new DriverException("Driver not available..!");
        }

        Ride ride = createRide(user, nearestDriver, pickupLatitude, pickupLongitude, destinationLatitude, destinationLongitude, pickUpArea, destinationArea);


        return ride;
    }

    @Override
    public Ride createRide(User user, Driver nearestDriver, double sourceLatitude, double sourceLongitude,
            double destinationLatitude, double destinationLongitude, String pickUpArea, String destinationArea) {
                
        Ride ride = new Ride();
        
        ride.setUser(user);
        ride.setDriver(nearestDriver);
        ride.setPickupLatitude(sourceLatitude);
        ride.setPickupLongitude(sourceLongitude);
        ride.setDestinationLatitude(destinationLatitude);
        ride.setDestinationLongitude(destinationLongitude);
        ride.setStatus(RideStatus.REQUESTED);
        ride.setPickupArea(pickUpArea);
        ride.setDestinationArea(destinationArea);

        return rideRepository.save(ride);

    }

    @Override
    public void acceptRide(Long rideId) {
        
        Ride ride = findRideById(rideId);

        Driver driver = ride.getDriver();

        ride.setStatus(RideStatus.ACCEPTED);
        driver.setCurrentRide(ride);

        Random random = new Random();

        int otp = random.nextInt(9000)+1000;
        ride.setOtp(otp);

        driverRepository.save(driver);
        rideRepository.save(ride);
        
    }

    @Override
    public void declinedRide(Long rideId, Long driverId) {
        
        Ride ride = new Ride();

        ride.getDeclinedDriver().add(driverId);

        List<Driver>availableDrivers = driverService.getAvailableDrivers(ride.getPickupLatitude(), ride.getPickupLongitude(), ride);

        Driver nearestDriver = driverService.findNearestDriver(availableDrivers,ride.getDestinationLatitude(), ride.getDestinationLongitude());

        ride.setDriver(nearestDriver);
        rideRepository.save(ride);
    }

    @Override
    public void startRide(Long rideId, int otp) {
        
        Ride ride  = findRideById(rideId);

        if(ride.getOtp()!=otp){
            throw new RideException("Invalid Otp..!");
        }

        
        ride.setStatus(RideStatus.STARTED);
        ride.setStartTime(LocalDateTime.now());
        System.out.println("Rodde"+ride);
        rideRepository.save(ride);

    }

    @Override
    public void completeRide(Long rideId) {
        
        Ride ride  =findRideById(rideId);

        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());

        double distance  = calculators.calculateDistance(ride.getPickupLatitude(), ride.getPickupLongitude(), ride.getDestinationLatitude(), ride.getDestinationLongitude());

        LocalDateTime start = ride.getStartTime();
        LocalDateTime end = ride.getEndTime();
        Duration duration = Duration.between(start, end);

        long miliseconds = duration.toMillis();

        double fare = calculators.getFare(distance);

        ride.setDistance(Math.round(distance*100)/100);
        ride.setFare(fare);
        ride.setDuration(miliseconds);

        Driver driver = ride.getDriver();
        driver.getRides().add(ride);
        driver.setCurrentRide(null);

        Integer driverRevenue = (int)(driver.getTotalRevenue()+Math.round(fare * .8));

        driver.setTotalRevenue(driverRevenue);

        driverRepository.save(driver);

        rideRepository.save(ride);
        
    }

    @Override
    public void cancelRide(Long rideId) {
        
        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.CANCELLED);
        rideRepository.save(ride);
    }

    @Override
    public Ride findRideById(Long rideId) {
        Optional<Ride>opt = rideRepository.findById(rideId);
        
        if(opt.isPresent()){
            return opt.get();
        }
        
        throw new RideException("Ride not present with id: "+rideId);
    }
    
}
