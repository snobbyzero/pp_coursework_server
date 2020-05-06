package com.example.pp_coursework_server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "count")
    private Long count;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "price")
    private Integer price;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "product_categories",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    List<Category> categories = new ArrayList<>();

    //@ManyToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    //List<User> users = new ArrayList<>();

    //@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinTable(name = "order_products",
    //        joinColumns = {@JoinColumn(name = "product_id")},
    //        inverseJoinColumns = {@JoinColumn(name = "order_id")})
    //List<Product> orders = new ArrayList<>();

    @Column(name = "image_path")
    private String imagePath;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonBackReference
    List<ProductCountOnly> productCountOnlyList = new ArrayList<>();

}
