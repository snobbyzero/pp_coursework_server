package com.example.pp_coursework_server.repository;

import com.example.pp_coursework_server.entity.Category;
import com.example.pp_coursework_server.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findAllByCategories(List<String> categories, Integer price1, Integer price2);
}
