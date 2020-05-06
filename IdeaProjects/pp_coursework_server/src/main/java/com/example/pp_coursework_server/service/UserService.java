package com.example.pp_coursework_server.service;

import com.example.pp_coursework_server.RegistrationForm;
import com.example.pp_coursework_server.entity.Role;
import com.example.pp_coursework_server.entity.User;
import com.example.pp_coursework_server.repository.RoleRepository;
import com.example.pp_coursework_server.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String addUser(RegistrationForm registrationForm) {
        String username = registrationForm.getUsername();
        if (userRepository.findByUsername(username).isPresent()) {
            return "This username is already in use";
        }
        User user = registrationForm.toUser(passwordEncoder);
        Role role = roleRepository.findByName("USER").orElseThrow();
        user.getRoles().add(role);
        userRepository.save(user);
        return "OK";
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User getUser(RegistrationForm registrationForm) {
        User user = userRepository.findByUsername(registrationForm.getUsername()).orElse(null);
        if (user != null) {
            if (passwordEncoder.matches(registrationForm.getPassword(), user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public String updateUser(User newUser) {
        User oldUser = userRepository.findById(newUser.getId()).orElseThrow();
        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setPhoneNumber(newUser.getPhoneNumber());
        oldUser.setAddress(newUser.getAddress());
        oldUser.setCity(newUser.getCity());

        if (newUser.getPassword() != null && !newUser.getPassword().equals("")) {
            oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        try {
            if (newUser.getUsername().equals(oldUser.getUsername()) || userRepository.findByUsername(newUser.getUsername()).isEmpty()) {
                oldUser.setUsername(newUser.getUsername());
            }
            else {
                throw new Exception();
            }
            userRepository.save(oldUser);
        } catch (Exception e) {
            return "NOT OK";
        }
        return "OK";
    }
}
