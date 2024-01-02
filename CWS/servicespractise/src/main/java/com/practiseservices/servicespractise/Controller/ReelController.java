package com.practiseservices.servicespractise.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practiseservices.servicespractise.Model.Comment;
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

    @GetMapping("reels-User/{userId}")
    public ResponseEntity<List<Reel>> findAllReelsOfUser(@PathVariable("userId")Long userId){

        List<Reel>reels = reelService.findUsersReel(userId);

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

    @GetMapping("save-reel/{reelId}")
    public ResponseEntity<Reel> saveReelHandler(@PathVariable("reelId")Long reelId,@RequestHeader("Authorization")String jwt){

        User user = userService.findUserByJwt(jwt);

        Reel reel = reelService.saveReel(reelId, user.getId());

        return new ResponseEntity<Reel>(reel, HttpStatus.OK);
    }

    @GetMapping("like-reel/{reelId}")
    public ResponseEntity<Reel> likeReelHandler(@PathVariable("reelId")Long reelId,@RequestHeader("Authorization")String jwt){
        User user = userService.findUserByJwt(jwt);

        Reel reel = reelService.likeReel(reelId, user.getId());

        return new ResponseEntity<Reel>(reel, HttpStatus.OK);

    }

    @GetMapping("get-reel/{reelId}")
    public ResponseEntity<Reel> findReelHandler(@PathVariable("reelId")Long reelId){

        Reel reel = reelService.findReelById(reelId);

        return new ResponseEntity<Reel>(reel,HttpStatus.FOUND);
    }

    @GetMapping("get-comment/{reelId}")
    public ResponseEntity<List<Comment>> findAllComment(@PathVariable("reelId")Long reelId){

        List<Comment>comments = reelService.findAllComments(reelId);

        return new ResponseEntity<List<Comment>>(comments, HttpStatus.FOUND);
    }
}
