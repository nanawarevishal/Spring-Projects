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

import com.zosh.ecommersyoutube.Exception.OrderException;
import com.zosh.ecommersyoutube.Exception.UserException;
import com.zosh.ecommersyoutube.Model.Address;
import com.zosh.ecommersyoutube.Model.Order;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Service.OrderService;
import com.zosh.ecommersyoutube.Service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,@RequestHeader("Authorization")String jwt)throws UserException{

        User user = userService.findUserProfileByJWT(jwt);
        
        Order order = orderService.createOrder(user, shippingAddress);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization")String jwt)throws UserException{
        User user = userService.findUserProfileByJWT(jwt);

        List<Order>orders = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<List<Order>>(orders,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderByIds(@PathVariable("id")Long orderID,@RequestHeader("Authorization")String jwt)throws OrderException{

        Order order = orderService.findOrderById(orderID);

        System.out.println("Order: "+order);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }
}
