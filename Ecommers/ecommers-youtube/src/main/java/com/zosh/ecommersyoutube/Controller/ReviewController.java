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
import com.zosh.ecommersyoutube.Model.Review;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Request.ReviewRequest;
import com.zosh.ecommersyoutube.Service.ReviewService;
import com.zosh.ecommersyoutube.Service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest request,@RequestHeader("Authorization")String jwt)throws UserException,ProductException{

        User user = userService.findUserProfileByJWT(jwt);

        Review review = reviewService.createReview(request, user);

        return new ResponseEntity<Review>(review,HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReview(@PathVariable("productId")Long productId)throws UserException,ProductException{

        List<Review>reviews = reviewService.getAllReview(productId);

        return new ResponseEntity<List<Review>>(reviews,HttpStatus.FOUND);
    }
}
