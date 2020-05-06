package com.example.pp_coursework_server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "products_count_only")
@NoArgsConstructor
public class ProductCountOnly {

    @Id
    @GeneratedValue
    @Column(name = "productcount_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    Product product;

    @Column(name = "count")
    Long count;

    @ManyToOne
    @JoinTable(name = "cart",
            joinColumns = {@JoinColumn(name = "productcount_id", referencedColumnName = "productcount_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    @JsonBackReference("u")
    User user;

    @ManyToOne
    @JoinTable(name = "order_cart",
            joinColumns = {@JoinColumn(name = "productcount_id", referencedColumnName = "productcount_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "order_id")})
    @JsonBackReference
    Order order;
}
