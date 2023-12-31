package com.zosh.ecommersyoutube.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommersyoutube.Exception.OrderException;
import com.zosh.ecommersyoutube.Model.Order;
import com.zosh.ecommersyoutube.Response.ApiResponse;
import com.zosh.ecommersyoutube.Service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<List<Order>>getAllOrdersHandler(){
        List<Order>orders = orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
    }


    @GetMapping("/{orderId}/confirmed")
    public ResponseEntity<Order>ConfimedOrderHandler(@PathVariable("orderId") Long OrderId,@RequestHeader("Authorization")String jwt)throws OrderException{

        Order order = orderService.confirmedOrder(OrderId);

        return new ResponseEntity<Order>(order,HttpStatus.OK);
    }

    @GetMapping("/{orderId}/shipped")
    public ResponseEntity<Order>ShippedOrderHandler(@PathVariable("orderId") Long OrderId,@RequestHeader("Authorization")String jwt)throws OrderException{

        Order order = orderService.shippedOrder(OrderId);

        return new ResponseEntity<Order>(order,HttpStatus.OK);
    }

    @GetMapping("/{orderId}/delivered")
    public ResponseEntity<Order>DeliveredOrderHandler(@PathVariable("orderId") Long OrderId,@RequestHeader("Authorization")String jwt)throws OrderException{

        Order order = orderService.deliveredOrder(OrderId);

        return new ResponseEntity<Order>(order,HttpStatus.OK);
    }

    @GetMapping("/{orderId}/cancel")
    public ResponseEntity<Order>CancelOrderHandler(@PathVariable("orderId") Long OrderId,@RequestHeader("Authorization")String jwt)throws OrderException{

        Order order = orderService.canceledOrder(OrderId);

        return new ResponseEntity<Order>(order,HttpStatus.OK);
    }

    @GetMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse>deleteOrderHandle(@PathVariable("orderId")Long OrderID,@RequestHeader("Authorization")String jwt)throws OrderException{

        orderService.deleteOrder(OrderID);

        ApiResponse res = new ApiResponse();
        res.setMsg("Order deleted Successfully..!");
        res.setStatus(true);

        return new ResponseEntity<ApiResponse>(res,HttpStatus.OK);

    }


}
