package com.example.pp_coursework_server;

import com.example.pp_coursework_server.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {

    @Size(min = 7, message = "min length of username is 7")
    private String username;

    @Size(min = 7, message = "min length of password is 7")
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password));
    }
}
