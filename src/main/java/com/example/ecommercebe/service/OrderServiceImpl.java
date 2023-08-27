package com.example.ecommercebe.service;
import com.example.ecommercebe.model.*;
import com.example.ecommercebe.repository.*;
import com.example.ecommercebe.exception.OrderException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private CartService cartService;
    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;

    public OrderServiceImpl(CartService cartService, AddressRepository addressRepository,
                            UserRepository userRepository, OrderRepository orderRepository,
                            OrderItemService orderItemService, OrderItemRepository orderItemRepository) {
        this.cartService = cartService;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {

        Optional<Order> opt = orderRepository.findById(orderId);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new OrderException("Cannot find order with id: " + orderId);
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {


        shippingAddress.setUser(user);

        Address address = addressRepository.save(shippingAddress);

        user.getAddress().add(address);

        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(item.getPrice());
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSize(item.getSize());
            orderItem.setUserId(item.getUserId());
            orderItem.setDiscountedPrice(item.getDiscountedPrice());

            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }

        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());
        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(createdOrder);

        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;
    }

    // get all orders which have been placed, confirmed, shipped and delivered
    @Override
    public List<Order> usersOrderHistory(Long userId) {

        return orderRepository.getUsersOrders(userId);
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {

        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");

        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {


        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");

        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {

        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");

        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {


        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");

        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {


        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deletedOrder(Long orderId) throws OrderException {

        Order order = findOrderById(orderId);
//        orderRepository.delete(order);
        orderRepository.deleteById(orderId);
    }
}
