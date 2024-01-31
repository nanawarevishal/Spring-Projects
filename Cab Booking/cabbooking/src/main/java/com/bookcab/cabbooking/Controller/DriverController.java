package com.bookcab.cabbooking.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookcab.cabbooking.Model.Driver;
import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Service.DriverService;

@RestController
@RequestMapping("/api/drivers/")
public class DriverController {
    
    
    private DriverService driverService;

    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }

    @GetMapping("profile")
    public ResponseEntity<Driver> getDriverProfileHandler(@RequestHeader("Authorization")String jwt){

        Driver driver = driverService.getRequestDriverProfile(jwt);

        return new ResponseEntity<Driver>(driver,HttpStatus.OK);
        
    }

    @GetMapping("current-ride/{driverId}")
    public ResponseEntity<Ride> getDriversCurrentRide(@PathVariable("driverId")Long driverId){

        Driver driver = driverService.findDriverById(driverId);

        Ride ride = driver.getCurrentRide();

        return new ResponseEntity<Ride>(ride, HttpStatus.OK);
    }

    @GetMapping("allocated-rides/{driverId}")
    public ResponseEntity<List<Ride>> getAllocatedRide(@PathVariable("driverId")Long driverId){

        List<Ride>rides = driverService.getAllocatedRide(driverId);

        return new ResponseEntity<List<Ride>>(rides,HttpStatus.OK);
    }

    @GetMapping("completed-rides/{driverId}")
    public ResponseEntity<List<Ride>> getCompletedRide(@PathVariable("driverId")Long driverId){

        List<Ride>rides = driverService.getCompletedRide(driverId);

        return new ResponseEntity<List<Ride>>(rides,HttpStatus.OK);
    }


}
