package com.example.pp_coursework_server.controller;

import com.example.pp_coursework_server.entity.Category;
import com.example.pp_coursework_server.service.CategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Category addCategory(@RequestBody String name) {
        return categoryService.addCategory(new Category(name));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }
}
