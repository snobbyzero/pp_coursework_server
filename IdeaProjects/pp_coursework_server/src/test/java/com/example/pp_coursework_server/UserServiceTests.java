package com.example.pp_coursework_server;

import com.example.pp_coursework_server.entity.User;
import com.example.pp_coursework_server.repository.UserRepository;
import com.example.pp_coursework_server.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class UserServiceTests {

    final String username = "testtest";
    final String password = "testtest";
    final long id = 100L;
    User user = User.builder()
            .id(id)
                .username(username)
                .password(password)
                .build();

    @MockBean
    private UserRepository userRepository;
    @Autowired
    UserService userService;

    @Test
    public void getUserById() {
        given(userRepository.findById(id)).willReturn(java.util.Optional.of(user));
        assertThat(userService.getUserById(id).getId()).isEqualTo(id);
    }

    @Test
    public void getUserByNonExistedId() {
        assertThrows(Exception.class, () -> userService.getUserById(id));
    }

    @Test
    public void registerWithExistingUsername() {
        given(userRepository.findByUsername(username)).willReturn(java.util.Optional.ofNullable(user));
        assertThat(userService.addUser(new RegistrationForm(username, password)).contains("This username is already in use"));
    }

}
