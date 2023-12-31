package com.zosh.ecommersyoutube.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Model.Product;
import com.zosh.ecommersyoutube.Model.Rating;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Repository.RatingRepository;
import com.zosh.ecommersyoutube.Request.RatingRequest;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Rating createRating(RatingRequest request, User user) throws ProductException {
        
        Product product = productService.findProductById(request.getProductId());

        Rating rating = new Rating();

        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(request.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);

    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        
        return ratingRepository.getAllProductsRating(productId);
    }
    
}
