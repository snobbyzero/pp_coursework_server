package com.example.pp_coursework_server.service;

import com.example.pp_coursework_server.entity.Category;
import com.example.pp_coursework_server.entity.Product;
import com.example.pp_coursework_server.entity.ProductCountOnly;
import com.example.pp_coursework_server.repository.ProductRepository;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategories(List<String> categories, Integer price1, Integer price2) {
        return productRepository.findAllByCategories(categories, price1, price2);
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findAllByNameStartingWith(name);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public String addProduct(Product product) {
        productRepository.save(product);
        return "OK";
    }

    public String updateProductCount(Long count, Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setCount(count);
        productRepository.save(product);
        return "OK";
    }

    public List<Product> getSortedProducts(String sortName) {
        switch (sortName) {
            case "In ascending order":
                return productRepository.findAllByOrderByPrice();
            case "In descending order":
                return productRepository.findAllByOrderByPriceDesc();
            case "A-Z":
                return productRepository.findAllByOrderByName();
            case "Z-A":
                return productRepository.findAllByOrderByNameDesc();
            default:
                return productRepository.findAll();
        }
    }
}
