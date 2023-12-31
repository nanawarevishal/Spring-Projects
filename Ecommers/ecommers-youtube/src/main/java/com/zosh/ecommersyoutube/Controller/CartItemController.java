package com.zosh.ecommersyoutube.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommersyoutube.Model.User;
import com.zosh.ecommersyoutube.Response.ApiResponse;
import com.zosh.ecommersyoutube.Service.CartItemService;
import com.zosh.ecommersyoutube.Service.UserService;

@RestController
@RequestMapping("/api/cart_item")
public class CartItemController {
    

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable("cartItemId")Long cartItemId,@RequestHeader("Authorization")String jwt){

        User user = userService.findUserProfileByJWT(jwt);

        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse response = new ApiResponse();
        response.setMsg("Item deleted from Cart....!");
        response.setStatus(true);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
}
