package com.zosh.ecommersyoutube.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Model.Cart;
import com.zosh.ecommersyoutube.Model.CartItem;
import com.zosh.ecommersyoutube.Model.Product;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Repository.CartRepository;
import com.zosh.ecommersyoutube.Request.AddItemRequest;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemService cartItemservice;

    @Autowired
    private ProductService productService;

    @Override
    public Cart createCart(User user) {
        
        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);

    }

    @Override
    public String addCartItem(Long userId, AddItemRequest request) throws ProductException {

        Cart cart = cartRepository.findByUserId(userId);

        // System.out.println("Cart: "+cart);

        Product product = productService.findProductById(request.getProductId());

        // System.out.println("Product: "+product);

        CartItem isPresent = cartItemservice.isCartItemExist(cart, product, request.getSize(), userId);

        // System.out.println("Is Present: "+isPresent);

        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setUserId(userId);

            int price = request.getQuantity()*product.getDiscountedPrice();

            cartItem.setPrice(price);
            cartItem.setSize(request.getSize());

            CartItem createdCartItem = cartItemservice.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }

        return "Item added to Cart";
    }

    @Override
    public Cart findUserCart(Long userId) {
        
        Cart cart = cartRepository.findByUserId(userId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for(CartItem cartItem : cart.getCartItems()){
            totalPrice += cartItem.getPrice();
            totalDiscountedPrice += cartItem.getDiscountedPrice();
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setDiscount(totalPrice-totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
