package com.zosh.ecommersyoutube.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zosh.ecommersyoutube.Model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    
}
