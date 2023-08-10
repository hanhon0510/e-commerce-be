package com.example.ecommercebe.service;

import com.example.ecommercebe.model.Address;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.repository.CartRepository;
import com.example.ecommercebe.exception.OrderException;
import com.example.ecommercebe.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private CartRepository cartRepository;

    private CartItemService cartItemService;

    private ProductService productService;

    public OrderServiceImpl(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartItemService = cartItemService;
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        return null;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return null;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public Order deletedOrder(Long orderId) throws OrderException {
        return null;
    }
}
