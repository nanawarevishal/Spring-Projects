package com.zosh.ecommersyoutube.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Exception.UserException;
import com.zosh.ecommersyoutube.Model.Rating;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Request.RatingRequest;
import com.zosh.ecommersyoutube.Service.RatingService;
import com.zosh.ecommersyoutube.Service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;


    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest ratingRequest,@RequestHeader("Authorization")String jwt)throws UserException,ProductException{

        User user = userService.findUserProfileByJWT(jwt);

        Rating rating = ratingService.createRating(ratingRequest, user);

        return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
        
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getproductRating(@PathVariable("productId")Long productId,@RequestHeader("Authorization")String jwt)throws UserException,ProductException{

        List<Rating>ratings= ratingService.getProductsRating(productId);

        return new ResponseEntity<List<Rating>>(ratings,HttpStatus.FOUND);
    }
}
