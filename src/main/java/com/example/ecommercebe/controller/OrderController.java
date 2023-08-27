package com.example.ecommercebe.controller;


import com.example.ecommercebe.exception.OrderException;
import com.example.ecommercebe.exception.UserException;
import com.example.ecommercebe.model.Address;
import com.example.ecommercebe.model.Order;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.service.OrderService;
import com.example.ecommercebe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
                                             @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.createOrder(user, shippingAddress);

        System.out.println("Order" + order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException {


        User user = userService.findUserProfileByJwt(jwt);

        List<Order> orders = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,
                                               @RequestHeader("Authorization") String jwt) throws OrderException, UserException {

        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }
}
