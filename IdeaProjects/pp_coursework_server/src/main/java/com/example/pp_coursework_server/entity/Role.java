package com.example.pp_coursework_server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    Long id;

    @Column(name = "name")
    String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    List<User> users;
}
