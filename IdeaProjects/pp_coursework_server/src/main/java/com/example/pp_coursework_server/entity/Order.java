package com.example.pp_coursework_server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_order")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonBackReference
    User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(name = "order_cart",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "productcount_id", referencedColumnName = "productcount_id")})
    List<ProductCountOnly> products = new ArrayList<>();

    @Column(name = "orderDate")
    Timestamp orderDate;

    @Column(name = "deliveryDate")
    Timestamp deliveryDate;

    @PrePersist
    public void prePersist()
    {
        orderDate = new Timestamp(System.currentTimeMillis());
        deliveryDate = new Timestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * user.getCity().getTimeOfDelivery());
    }
}
