package com.zosh.ecommersyoutube.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommersyoutube.Exception.ProductException;
import com.zosh.ecommersyoutube.Exception.UserException;
import com.zosh.ecommersyoutube.Model.Cart;
import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Request.AddItemRequest;
import com.zosh.ecommersyoutube.Response.ApiResponse;
import com.zosh.ecommersyoutube.Service.CartService;
import com.zosh.ecommersyoutube.Service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart>findUserCarts(@RequestHeader("Authorization")String jwt)throws UserException{

        User user = userService.findUserProfileByJWT(jwt);
            
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart,HttpStatus.FOUND);
    }


    @PutMapping("/add")
    public ResponseEntity<ApiResponse>addItemToCart(@RequestBody AddItemRequest request,@RequestHeader("Authorization")String jwt)throws UserException,ProductException{

        User user = userService.findUserProfileByJWT(jwt);

        cartService.addCartItem(user.getId(), request);

        ApiResponse response = new ApiResponse();
        response.setMsg("Item added to Cart....!");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
}
