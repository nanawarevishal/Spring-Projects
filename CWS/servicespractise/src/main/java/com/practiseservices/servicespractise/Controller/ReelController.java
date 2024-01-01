package com.practiseservices.servicespractise.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Model.Reel;
import com.practiseservices.servicespractise.Model.User;
import com.practiseservices.servicespractise.Response.ApiResponse;
import com.practiseservices.servicespractise.Services.ReelService;
import com.practiseservices.servicespractise.Services.UserService;

@RestController
@RequestMapping("/api/reel/")
public class ReelController {
    
    @Autowired
    private ReelService reelService;

    @Autowired
    private UserService userService;

    @PostMapping("createReel")
    public ResponseEntity<Reel> createReel(@RequestBody Reel reel,@RequestHeader("Authorization")String jwt){

        User user = userService.findUserByJwt(jwt);

        Reel createReel = reelService.createReel(reel, user.getId());

        return new ResponseEntity<Reel>(createReel, HttpStatus.CREATED);
    }

    @GetMapping("all-Reels")
    public ResponseEntity<List<Reel>> findAllReelsHandler(){

        List<Reel> reels = reelService.findAllReels();

        return new ResponseEntity<List<Reel>>(reels, HttpStatus.FOUND);
    }

    @GetMapping("reels-User")
    public ResponseEntity<List<Reel>> findAllReelsOfUser(@RequestHeader("Authorization")String jwt){

        User user = userService.findUserByJwt(jwt);

        List<Reel>reels = reelService.findUsersReel(user.getId());

        return new ResponseEntity<List<Reel>>(reels,  HttpStatus.FOUND);
    }

    @DeleteMapping("deleteReel/{reelId}")
    public ResponseEntity<ApiResponse> deleteReelHandler(@PathVariable("reelId")Long reelId,@RequestHeader("Authorization")String jwt){

        User user = userService.findUserByJwt(jwt);

        reelService.deleteReel(reelId,user.getId());

        ApiResponse response = new ApiResponse();
        response.setMsg("Reel deleted Successfully...!");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @GetMapping("save-reel/{reelID}")
    public ResponseEntity<Reel> saveReelHandler(@PathVariable("reelId")Long reelId,@RequestHeader("Authorization")String jwt){

        User user = userService.findUserByJwt(jwt);

        Reel reel = reelService.saveReel(reelId, user.getId());

        return new ResponseEntity<Reel>(reel, HttpStatus.OK);
    }

    public ResponseEntity<Reel> likeReelHandler(@PathVariable("reelID")Long reelId,@RequestHeader("Authorization")String jwt){
        User user = userService.findUserByJwt(jwt);

        Reel reel = reelService.likeReel(reelId, user.getId());

        return new ResponseEntity<Reel>(reel, HttpStatus.OK);

    }
}
