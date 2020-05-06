package com.example.pp_coursework_server.controller;

import com.example.pp_coursework_server.entity.Category;
import com.example.pp_coursework_server.entity.Product;
import com.example.pp_coursework_server.service.ProductService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(params = {"categories", "price1", "price2"})
    public List<Product> getProductsByCategories(
            @RequestParam(defaultValue = "", name = "categories") List<String> categories,
            @RequestParam(defaultValue = "0", name = "price1") Integer price1,
            @RequestParam(defaultValue = "100000", name = "price2") Integer price2) {
        return productService.getProductsByCategories(categories, price1, price2);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(params = "name")
    public List<Product> getProductsByName(@RequestParam String name) {
        return productService.getProductsByName(name);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public String addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(path = "/{id}/count", produces = "application/json", consumes = "application/json")
    public String updateProduct(@RequestBody Long count, @PathVariable Long id) {
        return productService.updateProductCount(count, id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/sort")
    public List<Product> getSortedProducts(@RequestParam String sortName) {
        return productService.getSortedProducts(sortName);
    }
}
