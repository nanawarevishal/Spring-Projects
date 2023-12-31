package com.zosh.ecommersyoutube.Service;

import java.util.List;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Model.Review;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Request.ReviewRequest;

public interface ReviewService {
    
    public Review createReview(ReviewRequest request,User user)throws ProductException;

    public List<Review> getAllReview(Long productId);
}
