package com.example.pp_coursework_server.repository;

import com.example.pp_coursework_server.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
