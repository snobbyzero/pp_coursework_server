package com.example.pp_coursework_server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(name = "name", unique = true)
    @NonNull
    private String name;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    List<Product> products = new ArrayList<>();
}
