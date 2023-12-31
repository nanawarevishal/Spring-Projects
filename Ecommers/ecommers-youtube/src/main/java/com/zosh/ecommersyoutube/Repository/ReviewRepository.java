package com.zosh.ecommersyoutube.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zosh.ecommersyoutube.Model.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    
    // @Query("Select r from Review Where r.product.id =: productId")
    // public List<Review> getAllProductsReview(@Param("productId")Long productId);

    @Query("SELECT r FROM Review r WHERE r.product.id = :productId")
    public List<Review> getAllProductsReview(@Param("productId") Long productId);
}
