package com.example.pp_coursework_server.repository;

import com.example.pp_coursework_server.entity.Category;
import com.example.pp_coursework_server.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAllByCategories(List<String> categories, Integer price1, Integer price2) {
        if (price1 == null) {
            price1 = 0;
        }
        if (price2 == null) {
            price2 = Integer.MAX_VALUE;
        }
        List<Product> products = productRepository.findAllByPriceBetween(price1, price2);
        List<Product> satisfiedProducts = new ArrayList<>();
        for (var product : products) {
            List<String> productsNames = new ArrayList<>();
            product.getCategories().forEach(category -> productsNames.add(category.getName()));
           if (productsNames.containsAll(categories)) {
               satisfiedProducts.add(product);
           }
        }
        return satisfiedProducts;
    }
}
