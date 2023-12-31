package com.zosh.ecommersyoutube.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Model.OrderItem;
import com.zosh.ecommersyoutube.Repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        
        return orderItemRepository.save(orderItem);
    }
    
}
