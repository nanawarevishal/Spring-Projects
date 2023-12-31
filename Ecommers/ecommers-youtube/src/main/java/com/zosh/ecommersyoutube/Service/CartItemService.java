package com.zosh.ecommersyoutube.Service;

import com.zosh.ecommersyoutube.Exception.CartItemException;
import com.zosh.ecommersyoutube.Exception.UserException;
import com.zosh.ecommersyoutube.Model.Cart;
import com.zosh.ecommersyoutube.Model.CartItem;
import com.zosh.ecommersyoutube.Model.Product;

public interface CartItemService {
    
    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId,Long Id,CartItem cartItem)throws CartItemException,UserException;

    public CartItem isCartItemExist(Cart cart,Product product,String size,Long userId);

    public void removeCartItem(Long userId,Long cartItemId)throws CartItemException,UserException;

    public CartItem findCartItemById(Long cartItemId)throws CartItemException;
}
