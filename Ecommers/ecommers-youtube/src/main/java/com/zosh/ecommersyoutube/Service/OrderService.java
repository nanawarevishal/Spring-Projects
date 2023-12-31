package com.zosh.ecommersyoutube.Service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Exception.OrderException;
import com.zosh.ecommersyoutube.Model.Address;
import com.zosh.ecommersyoutube.Model.Order;
import com.zosh.ecommersyoutube.Model.User;

@Service
public interface OrderService {

    public Order createOrder(User user,Address shippingAddress);
    
    public Order findOrderById(Long OrderId)throws OrderException;

    public List<Order> usersOrderHistory(Long OrderId)throws OrderException;

    public Order placedOrder(Long OrderId)throws OrderException;

    public Order confirmedOrder(Long OrderId) throws OrderException;

    public Order shippedOrder(Long OrderId)throws OrderException;

    public Order deliveredOrder(Long OrderId)throws OrderException;

    public Order canceledOrder(Long OrderId)throws OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(Long OrderId)throws OrderException;
    
}
