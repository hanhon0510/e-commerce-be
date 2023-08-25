package com.example.ecommercebe.controller;

import com.example.ecommercebe.exception.CartItemException;
import com.example.ecommercebe.exception.UserException;
import com.example.ecommercebe.model.CartItem;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.response.ApiResponse;
import com.example.ecommercebe.service.CartItemService;
import com.example.ecommercebe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
                                                      @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {

        User user = userService.findUserProfileByJwt(jwt);

        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setStatus(true);
        res.setMessage("Item deleted from cart");

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId,
                                                      @RequestBody CartItem cartItem,
                                                      @RequestHeader("Authorization") String jwt) throws UserException, CartItemException {



        User user = userService.findUserProfileByJwt(jwt);

        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }
}
