package com.example.ecommercebe.service;

import com.example.ecommercebe.model.Address;
import com.example.ecommercebe.model.User;
import com.example.exception.OrderException;
import com.example.model.Order;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user, Address shippingAddress);

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws  OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order canceledOrder(Long orderId) throws OrderException;

    public List<Order> getAllOrders();

    public Order deletedOrder(Long orderId) throws OrderException;


}
