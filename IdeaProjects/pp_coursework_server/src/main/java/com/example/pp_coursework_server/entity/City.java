package com.example.pp_coursework_server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue
    @Column(name = "city_id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "time_of_delivery")
    Integer timeOfDelivery;

    @OneToMany
    @JoinColumn(name = "city_id")
    @JsonBackReference
    List<User> users;

}
