package com.example.pp_coursework_server.service;

import com.example.pp_coursework_server.entity.Category;
import com.example.pp_coursework_server.entity.Order;
import com.example.pp_coursework_server.entity.ProductCountOnly;
import com.example.pp_coursework_server.entity.User;
import com.example.pp_coursework_server.repository.CategoryRepository;
import com.example.pp_coursework_server.repository.ProductRepository;
import com.example.pp_coursework_server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountingService {

    final UserRepository userRepository;
    final ProductRepository productRepository;
    final CategoryRepository categoryRepository;

    public AccountingService(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Double getAverageTotalPrice() {
        List<Order> orders = new ArrayList<>();
        userRepository.findAll().forEach(user -> orders.addAll(user.getOrderHistory()));
        List<Long> prices = new ArrayList<>();
        orders.forEach(order -> prices.add(order.getProducts().stream().mapToLong(p -> p.getCount() * p.getProduct().getPrice()).sum()));
        return prices.stream().mapToDouble(price -> price).sum() / prices.size();
    }

    public Double getAverageTotalWeight() {
        List<Order> orders = new ArrayList<>();
        userRepository.findAll().forEach(user -> orders.addAll(user.getOrderHistory()));
        List<Double> weights = new ArrayList<>();
        orders.forEach(order -> weights.add(order.getProducts().stream().mapToDouble(p -> p.getCount() * p.getProduct().getWeight()).sum()));
        return weights.stream().mapToDouble(price -> price).sum() / weights.size();
    }

    public Long getCategoryProfit(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        //userRepository.findAll().forEach(user -> orders.addAll(user.getOrderHistory()));
        return userRepository.findAll().stream().mapToLong(user -> user.getOrderHistory().stream().mapToLong(order -> order.getProducts().stream().filter(p -> p.getProduct().getCategories().contains(category)).mapToLong(p -> p.getCount()*p.getProduct().getPrice()).sum()).sum()).sum();
    }
}
