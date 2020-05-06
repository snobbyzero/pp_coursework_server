package com.example.pp_coursework_server;

import com.example.pp_coursework_server.entity.*;
import com.example.pp_coursework_server.repository.CategoryRepository;
import com.example.pp_coursework_server.repository.ProductRepository;
import com.example.pp_coursework_server.repository.UserRepository;
import com.example.pp_coursework_server.service.OrderService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class DataLoader implements ApplicationRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    public DataLoader(ProductRepository productRepository, CategoryRepository categoryRepository, UserRepository userRepository, OrderService orderService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    @Override
    public void run(ApplicationArguments args) {
        /*
        List<Category> categories = categoryRepository.findAll();
        for (int i = 0; i < 500; i ++ ) {
            Random random = new Random();
            int index = random.nextInt(categories.size());
            int num = random.nextInt();
            int price = random.nextInt(5000);
            Category category = categories.get(index);
            Product product1 = Product.builder()
                    .name(num + "")
                    .description("description")
                    .count(100L)
                    .weight(1F)
                    .price(price)
                    .categories(Arrays.asList(category))
                    .imagePath("")
                    .build();
            productRepository.save(product1);

        }

        /*
        List<Product> products = productRepository.findAll();
        User user = userRepository.findByUsername("adminadmin").orElseThrow();
        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            Random rnd1 = new Random();
            int count = rnd1.nextInt(10);
            int index = rnd1.nextInt(products.size());
            orderService.addNewOrder(user.getId(),
                    Arrays.asList(ProductCountOnly.builder().count((long) count).product(products.get(index)).build()));
        }

         */
    }
}
