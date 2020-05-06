package com.example.pp_coursework_server.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Builder
@Table(name = "user_information")
@Entity
@Data
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    @Size(min = 7, message = "username length shoud be more than 6")
    private String username;
    //@Pattern(regexp=".+@.+\\..+")
    @Column(name = "email")
    private String email;

    //@Size(min = 7, message = "password length should be mre than 7")
    @Column(name = "password")
    @Size(min = 7, message = "password length should be more than 6")
    private String password;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(name = "cart",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "productcount_id", referencedColumnName = "productcount_id")})
    List<ProductCountOnly> cart = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    @Fetch(value = FetchMode.SUBSELECT)
    List<Role> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    List<Order> orderHistory = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
         */
        List<SimpleGrantedAuthority> authorities = new ArrayList();
        roles.forEach(x -> authorities.add(new SimpleGrantedAuthority("ROLE_" + x.getName())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
