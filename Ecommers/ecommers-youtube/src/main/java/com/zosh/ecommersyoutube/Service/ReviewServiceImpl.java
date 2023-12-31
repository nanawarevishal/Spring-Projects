package com.zosh.ecommersyoutube.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Model.Product;
import com.zosh.ecommersyoutube.Model.Review;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Repository.ProductRepository;
import com.zosh.ecommersyoutube.Repository.ReviewRepository;
import com.zosh.ecommersyoutube.Request.ReviewRequest;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Override
    public Review createReview(ReviewRequest request, User user) throws ProductException {
        
        Product product = productService.findProductById(request.getProductId());

        Review review = new Review();

        review.setUser(user);
        review.setProduct(product);
        review.setReview(request.getReview());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        
        return reviewRepository.getAllProductsReview(productId);
    }
    
}
