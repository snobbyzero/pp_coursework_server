package com.example.pp_coursework_server;

import com.example.pp_coursework_server.entity.Product;
import com.example.pp_coursework_server.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @MockBean
    ProductRepository productRepository;

    @Autowired
    MockMvc mockMvc;

    Product product = Product.builder()
            .name("name")
            .weight(3.5F)
            .count(3L)
            .price(1000)
            .description("descr")
            .build();

    @Test
    @WithMockUser(username = "adminadmin", password = "adminadmin", roles = "ADMIN")
    public void addProductWithAdminPrivilege() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        assertThat(mockMvc.perform(post("/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk()));
    }

    @Test
    public void getProductsByName() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String name = "name";
        List<Product> productList = Arrays.asList(Product.builder().name(name).build());
        given(productRepository.findAllByNameStartingWith(name)).willReturn(productList);
        assertThat(mockMvc.perform(get("/products")
                .param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(productList))));
    }
}
