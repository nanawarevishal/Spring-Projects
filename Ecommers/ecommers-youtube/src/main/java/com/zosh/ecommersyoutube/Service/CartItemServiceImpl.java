package com.zosh.ecommersyoutube.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommersyoutube.Exception.CartItemException;
import com.zosh.ecommersyoutube.Exception.UserException;
import com.zosh.ecommersyoutube.Model.Cart;
import com.zosh.ecommersyoutube.Model.CartItem;
import com.zosh.ecommersyoutube.Model.Product;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Repository.CartItemRepository;
import com.zosh.ecommersyoutube.Repository.CartRepository;


@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemrepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;
    

    @Override
    public CartItem createCartItem(CartItem cartItem) {
       
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
        
        CartItem createdCartItem = cartItemrepository.save(cartItem);

        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long Id, CartItem cartItem) throws CartItemException, UserException {
        
        CartItem item = findCartItemById(Id);
        User user = userService.findUserById(item.getUserId());

        if(user.getId().equals(userId)){

            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());


            return cartItemrepository.save(item);

        }

        else{

            throw new CartItemException("Access denied");
        }

    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        
        CartItem cartItem = cartItemrepository.isCartItemExist(cart, product, size, userId);

        System.out.println(cartItem);

        return cartItem;

        // if(cartItem!=null){
        //     return cartItem;
        // }
        // else{

        //     throw new CartItemException("Cart-Item doesnt exists....!");
        // }

    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        
        CartItem cartItem = findCartItemById(cartItemId);

        User user = userService.findUserById(cartItem.getUserId());

        User reqUser = userService.findUserById(userId);

        if(user.getId().equals(reqUser.getId())){
            cartItemrepository.delete(cartItem);
        }

        else{
            
            throw new UserException("You cann't remove another Users items....!");
        }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
       
        Optional<CartItem>opt = cartItemrepository.findById(cartItemId);

        if(opt.isPresent()){

            return opt.get();
        }

        else{

            throw new CartItemException("Cart Item not found with id: "+cartItemId);
        }
        
    }
    
}
