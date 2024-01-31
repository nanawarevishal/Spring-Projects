package com.bookcab.cabbooking.DTOs.Mapper;

import com.bookcab.cabbooking.DTOs.DriverDTO;
import com.bookcab.cabbooking.DTOs.RideDTO;
import com.bookcab.cabbooking.DTOs.UserDTO;
import com.bookcab.cabbooking.Model.Driver;
import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Model.User;

public class DtoMapper {
    
    public static DriverDTO toDriverDTO(Driver driver){

        DriverDTO driverDTO = new DriverDTO();

        driverDTO.setId(driver.getId());
        driverDTO.setEmail(driver.getEmail());
        driverDTO.setFirstName(driver.getFirstName());
        driverDTO.setLastName(driver.getLastName());
        driverDTO.setLatitude(driver.getLatitude());
        driverDTO.setLongitude(driver.getLongitude());
        driverDTO.setMobile(driver.getMobile());
        driverDTO.setRating(driver.getRating());
        driverDTO.setRole(driver.getRole());
        driverDTO.setVehicle(driver.getVehicle());

        return driverDTO;
    }


    public static UserDTO toUserDTO(User user){
        
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setId(user.getId());
        userDTO.setLastName(user.getLastName());
        userDTO.setMobile(user.getMobile());

        return userDTO;
    }

    public static RideDTO toRideDTO(Ride ride){

        DriverDTO driverDTO = toDriverDTO(ride.getDriver());
        UserDTO userDTO = toUserDTO(ride.getUser());

        
        RideDTO rideDTO = new RideDTO();
        
        rideDTO.setDestinationArea(ride.getDestinationArea());
        rideDTO.setDestinationLatitude(ride.getDestinationLatitude());
        rideDTO.setDestinationLongitude(ride.getDestinationLongitude());
        rideDTO.setDistance(ride.getDistance());
        rideDTO.setDuration(ride.getDuration());
        rideDTO.setEndTime(ride.getEndTime());
        rideDTO.setFare(ride.getFare());
        rideDTO.setId(ride.getId());
        rideDTO.setOtp(ride.getOtp());
        rideDTO.setPaymentDetails(ride.getPaymentDetails());
        rideDTO.setPickUpLongitude(ride.getPickupLongitude());
        rideDTO.setPickUplatitude(ride.getPickupLatitude());
        rideDTO.setPickupArea(ride.getPickupArea());
        rideDTO.setStartTime(ride.getStartTime());
        rideDTO.setStatus(ride.getStatus());
        rideDTO.setUser(userDTO);
        rideDTO.setDriver(driverDTO);

        return rideDTO;
    }


}
