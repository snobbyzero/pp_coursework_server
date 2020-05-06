package com.example.pp_coursework_server;

import com.example.pp_coursework_server.entity.Product;
import com.example.pp_coursework_server.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTests {

    String username = "testtest";
    String password = "testtest";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getUserShouldReturnUnauthorized() {
        assertThat(testRestTemplate.getForObject("http://localhost:" + port + "/user/1", String.class).contains("Unauthorized"));
    }

    @Test
    public void registerShouldReturnOK() {
        RegistrationForm registrationForm = new RegistrationForm(username, password);
        assertThat(testRestTemplate.postForObject("http://localhost:" + port + "/user/register", registrationForm, String.class).contains("OK"));
    }

    @Test
    public void loginShouldReturnUser() {
        assertThat(testRestTemplate.getForObject("http://localhost:" + port + "/user/login?username=" + username + "&password=" + password, User.class) != null);
    }

    @Test
    public void addProductShouldReturnAccessDenied() {
        Product product = new Product();
        assertThat(testRestTemplate.postForObject("http://localhost:" + port + "/product", product, String.class).contains("Access denied"));
    }
}
