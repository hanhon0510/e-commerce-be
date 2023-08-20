package com.example.ecommercebe.service;

import com.example.ecommercebe.exception.ProductException;
import com.example.ecommercebe.model.Cart;
import com.example.ecommercebe.model.CartItem;
import com.example.ecommercebe.model.Product;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.repository.CartItemRepository;
import com.example.ecommercebe.repository.CartRepository;
import com.example.ecommercebe.request.AddItemRequest;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemService cartItemService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
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

        Product product = productService.findProductById(req.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

        if (isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);

            int price = req.getQuantity() * product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }

        return "Item added to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {

        Cart cart = cartRepository.findUserById(userId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice = totalPrice + cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
            totalItem = totalItem + cartItem.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice - totalDiscountedPrice);

        return cartRepository.save(cart);
    }


}
