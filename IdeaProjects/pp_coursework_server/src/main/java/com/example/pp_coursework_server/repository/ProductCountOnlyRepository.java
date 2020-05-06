package com.example.pp_coursework_server.repository;

import com.example.pp_coursework_server.entity.ProductCountOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCountOnlyRepository extends JpaRepository<ProductCountOnly, Long> {
}
