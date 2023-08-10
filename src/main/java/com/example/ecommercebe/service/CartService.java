package com.example.ecommercebe.service;

import com.example.ecommercebe.exception.ProductException;
import com.example.ecommercebe.model.Cart;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.request.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);
}
