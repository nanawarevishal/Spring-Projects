package com.zosh.ecommersyoutube.Service;

import java.util.List;


import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Model.Rating;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Request.RatingRequest;

public interface RatingService {
    
    public Rating createRating(RatingRequest request,User user)throws ProductException;

    public List<Rating> getProductsRating(Long productId);
}
