package com.example.ecommercebe.service;

import com.example.ecommercebe.exception.ProductException;
import com.example.ecommercebe.model.Cart;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.repository.CartItemRepository;
import com.example.ecommercebe.repository.CartRepository;
import com.example.ecommercebe.request.AddItemRequest;

public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {

        Cart cart = new Cart();
        cart.setUser(user);

        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {

        Cart cart = cartRepository.findUserById(userId);



        return null;
    }

    @Override
    public Cart findUserCart(Long userId) {
        return null;
    }


}
