package com.zosh.ecommersyoutube.Service;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Model.Cart;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Request.AddItemRequest;

public interface CartService {
    

    public Cart createCart(User user);

    public String addCartItem(Long userId,AddItemRequest request)throws ProductException;

    public Cart findUserCart(Long userId);
}
