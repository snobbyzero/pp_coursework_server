package com.example.pp_coursework_server.service;

import com.example.pp_coursework_server.entity.Category;
import com.example.pp_coursework_server.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        if (categoryRepository.findByName(category.getName()) == null) {
            categoryRepository.save(category);
            return category;
        }
        return null;
    }
}
