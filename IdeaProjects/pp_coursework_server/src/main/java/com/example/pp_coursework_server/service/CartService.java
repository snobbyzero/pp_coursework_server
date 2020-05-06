package com.example.pp_coursework_server.service;

import com.example.pp_coursework_server.entity.Product;
import com.example.pp_coursework_server.entity.ProductCountOnly;
import com.example.pp_coursework_server.entity.User;
import com.example.pp_coursework_server.repository.ProductRepository;
import com.example.pp_coursework_server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public String addToCart(Long user_id, List<ProductCountOnly> cart) {
        User user = userRepository.findById(user_id).orElseThrow();
        user.getCart().clear();
        user.getCart().addAll(cart);
        userRepository.save(user);
        return "OK";
    }

    public String clearCart(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow();
        user.getCart().clear();
        userRepository.save(user);
        return "OK";
    }

    public List<ProductCountOnly> getProductsFromCart(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow();
        return user.getCart();
    }
}
