package com.example.ecommercebe.service;

import com.example.ecommercebe.exception.CartItemException;
import com.example.ecommercebe.exception.ProductException;
import com.example.ecommercebe.exception.UserException;
import com.example.ecommercebe.model.Cart;
import com.example.ecommercebe.model.CartItem;
import com.example.ecommercebe.model.Product;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.repository.CartItemRepository;
import com.example.ecommercebe.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }
    @Override
    public CartItem createCartItem(CartItem cartItem) {

        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

        CartItem createdCartItem = cartItemRepository.save(cartItem);

        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item = findCartItemById(id);

        User user = userService.findUserById(item.getUserId());

        if (user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
        }

        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);

        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        CartItem cartItem = findCartItemById(cartItemId);

        User user = userService.findUserById(cartItem.getUserId());

        User reqUser = userService.findUserById(userId);

        if (user.getId().equals(reqUser.getId())) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            throw new UserException("Cannot remove item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {

        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new CartItemException("Cart Item not found with id: " + cartItemId);
    }
}
