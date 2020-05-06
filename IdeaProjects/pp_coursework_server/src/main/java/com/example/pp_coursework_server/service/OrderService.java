package com.example.pp_coursework_server.service;

import com.example.pp_coursework_server.entity.Order;
import com.example.pp_coursework_server.entity.ProductCountOnly;
import com.example.pp_coursework_server.entity.User;
import com.example.pp_coursework_server.repository.OrderRepository;
import com.example.pp_coursework_server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderService(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public String addNewOrder(Long id, List<ProductCountOnly> cart) {
        User user = userRepository.findById(id).orElseThrow();
        Order order = new Order();
        order.getProducts().addAll(cart);
        order.setUser(user);
        user.getCart().clear();
        user.getOrderHistory().add(order);
        userRepository.save(user);
        return "OK";
    }

    public String changeOrder(Order order) {
        //User user = userRepository.findById(id).orElseThrow();
        Order oldOrder = orderRepository.findById(order.getId()).orElseThrow();
        order.getProducts().forEach(x -> System.out.println(x.getProduct().getName()));
        if (order.getProducts().size() > 0) {
            oldOrder.getProducts().clear();
            oldOrder.getProducts().addAll(order.getProducts());
            orderRepository.save(oldOrder);
        } else {
            orderRepository.delete(oldOrder);
        }
        return "OK";
    }

    public List<Order> getOrderHistory(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return user.getOrderHistory();
    }
}
