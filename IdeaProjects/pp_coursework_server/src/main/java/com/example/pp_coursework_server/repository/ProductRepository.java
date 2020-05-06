package com.example.pp_coursework_server.repository;

import com.example.pp_coursework_server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    List<Product> findAllByNameStartingWith(String name);
    List<Product> findAllByPriceBetween(Integer price1, Integer price2);
    List<Product> findAllByOrderByPrice();
    List<Product> findAllByOrderByPriceDesc();
    List<Product> findAllByOrderByName();
    List<Product> findAllByOrderByNameDesc();
}
