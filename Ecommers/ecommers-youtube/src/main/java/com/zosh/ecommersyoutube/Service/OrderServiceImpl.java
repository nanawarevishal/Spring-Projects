package com.zosh.ecommersyoutube.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Exception.OrderException;
import com.zosh.ecommersyoutube.Model.Address;
import com.zosh.ecommersyoutube.Model.Cart;
import com.zosh.ecommersyoutube.Model.CartItem;
import com.zosh.ecommersyoutube.Model.Order;
import com.zosh.ecommersyoutube.Model.OrderItem;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Repository.AddressRepository;
import com.zosh.ecommersyoutube.Repository.CartRepository;
import com.zosh.ecommersyoutube.Repository.OrderItemRepository;
import com.zosh.ecommersyoutube.Repository.OrderRepository;
import com.zosh.ecommersyoutube.Repository.UserRepositoty;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepositoty userRepositoty;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired 
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;


    @Override
    public Order createOrder(User user, Address shippingAddress) {
        
        shippingAddress.setUser(user);

        Address address = addressRepository.save(shippingAddress);

        user.getAddress().add(address);
        userRepositoty.save(user);

        Cart cart = cartService.findUserCart(user.getId());

        List<OrderItem>orderItems = new ArrayList<>();
        
        for(CartItem item : cart.getCartItems()){

            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());

            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);


        }

        Order createdOrder = new Order();

        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());

        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setPaymentStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(createdOrder);

        for(OrderItem item : orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;

    }

    @Override
    public Order findOrderById(Long OrderId) throws OrderException {
        
        Optional<Order>opt = orderRepository.findById(OrderId);

        if(opt.isPresent())
            return opt.get();

        else{
            throw new OrderException("Order not exists with id: "+OrderId);
        }
    }

    @Override
    public List<Order> usersOrderHistory(Long OrderId) throws OrderException {
        
        List<Order> orderHistory = orderRepository.getUserOrders(OrderId);
        return orderHistory;
    }

    @Override
    public Order placedOrder(Long OrderId) throws OrderException {
        Order order = findOrderById(OrderId);

        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setPaymentStatus("COMLETED");

        return orderRepository.save(order);
    }

    @Override
    public Order confirmedOrder(Long OrderId) throws OrderException {
        
        Order order = findOrderById(OrderId);
        order.setOrderStatus("CONFIRMED");

        return orderRepository.save(order);

    }

    @Override
    public Order shippedOrder(Long OrderId) throws OrderException {
        
        Order order = findOrderById(OrderId);
        order.setOrderStatus("SHIPPED");
        
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long OrderId) throws OrderException {
        Order order = findOrderById(OrderId);
        order.setOrderStatus("DELIVERED");
        
        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long OrderId) throws OrderException {
        Order order = findOrderById(OrderId);
        order.setOrderStatus("CANCELED");
        
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long OrderId) throws OrderException {
        Order order = findOrderById(OrderId);

        if(order == null){
            throw new OrderException("Order not found with id: "+OrderId);
        }

       orderRepository.deleteById(OrderId);
    }
    
}
