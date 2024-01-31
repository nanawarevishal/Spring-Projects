package com.bookcab.cabbooking.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookcab.cabbooking.DTOs.RideDTO;
import com.bookcab.cabbooking.DTOs.Mapper.DtoMapper;
import com.bookcab.cabbooking.Model.Driver;
import com.bookcab.cabbooking.Model.Ride;
import com.bookcab.cabbooking.Model.User;
import com.bookcab.cabbooking.Request.RideRequest;
import com.bookcab.cabbooking.Request.StarRideRequest;
import com.bookcab.cabbooking.Response.MessageResponse;
import com.bookcab.cabbooking.Service.DriverService;
import com.bookcab.cabbooking.Service.RideService;
import com.bookcab.cabbooking.Service.UserService;

@RestController
@RequestMapping("/api/rides/")
public class RideController {
    
    private RideService rideService;

    private DriverService driverService;

    private UserService userService;



    public RideController(RideService rideService,UserService userService,DriverService driverService){
        this.rideService = rideService;
        this.userService = userService;
        this.driverService = driverService;
    }

    @PostMapping("ride-request")
    public ResponseEntity<RideDTO> userRequestRideHandler(@RequestBody RideRequest rideRequest,@RequestHeader("Authorization")String jwt){

        User user = userService.getReqUserProfile(jwt);

        Ride ride = rideService.requestRide(rideRequest, user);

        RideDTO rideDto = DtoMapper.toRideDTO(ride);

        return new ResponseEntity<RideDTO>(rideDto,HttpStatus.ACCEPTED);

    }

    @PutMapping("accept-ride/{rideId}")
    public ResponseEntity<MessageResponse> acceptRidehandler(@PathVariable("rideId") Long rideId){

        rideService.acceptRide(rideId);

        MessageResponse response = new MessageResponse("Ride Accepted by Driver..!");

        return new ResponseEntity<MessageResponse>(response,HttpStatus.ACCEPTED);
    }

    @PutMapping("decline-ride/{rideId}")
    public ResponseEntity<MessageResponse> declineRidehandler(@PathVariable("rideId") Long rideId,@RequestHeader("Authorization")String jwt){

        Driver driver = driverService.getRequestDriverProfile(jwt);

        rideService.declinedRide(rideId, driver.getId());

        MessageResponse response = new MessageResponse("Ride declined by Driver..!");

        return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
    }


    @PutMapping("start-ride/{rideId}")
    public ResponseEntity<MessageResponse> startRidehandler(@PathVariable("rideId") Long rideId,@RequestBody StarRideRequest request){

        rideService.startRide(rideId, request.getOtp());

        MessageResponse response = new MessageResponse("Ride is started...!");

        return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
    }


    @PutMapping("complete-ride/{rideId}")
    public ResponseEntity<MessageResponse> completeRidehandler(@PathVariable("rideId") Long rideId){

        rideService.completeRide(rideId);

        System.out.println("Ride is completed..!");

        MessageResponse response = new MessageResponse("Ride is Completed...!");

        return new ResponseEntity<MessageResponse>(response,HttpStatus.OK);
    }

    @GetMapping("getRide/{rideId}")
    public ResponseEntity<RideDTO> findRideByHandler(@PathVariable("rideId") Long rideId,@RequestHeader("Authorization")String jwt){

        User user = userService.getReqUserProfile(jwt);

        Ride ride = rideService.findRideById(rideId);

        RideDTO rideDTO = DtoMapper.toRideDTO(ride);

        return new ResponseEntity<RideDTO>(rideDTO,HttpStatus.ACCEPTED);
    }
}
