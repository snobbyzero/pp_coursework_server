package com.example.pp_coursework_server.controller;

import com.example.pp_coursework_server.RegistrationForm;
import com.example.pp_coursework_server.entity.Order;
import com.example.pp_coursework_server.entity.Product;
import com.example.pp_coursework_server.entity.ProductCountOnly;
import com.example.pp_coursework_server.entity.User;
import com.example.pp_coursework_server.service.CartService;
import com.example.pp_coursework_server.service.OrderService;
import com.example.pp_coursework_server.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;
    CartService cartService;
    OrderService orderService;
    @Resource(name="authenticationManager")
    AuthenticationManager authManager;

    public UserController(UserService userService, CartService cartService, OrderService orderService) {
        this.userService = userService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/login")
    public ResponseEntity<User> getUser(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authReq =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth;
        auth = authManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);

        return new ResponseEntity<>(userService.getUser(new RegistrationForm(username, password)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/cart")
    public List<ProductCountOnly> getCart(@PathVariable Long id) {
        SecurityContext sc = SecurityContextHolder.getContext();
        return cartService.getProductsFromCart(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/{userId}/cart", produces = "application/json", consumes = "application/json")
    public String addToCart(@PathVariable Long userId, @RequestBody List<ProductCountOnly> cart) { return cartService.addToCart(userId, cart); }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping(value = "/{userId}/cart")
    public String clearCart(@PathVariable Long userId) {
        return cartService.clearCart(userId);
    }

    @PostMapping(value = "/registration", produces = "application/json", consumes = "application/json")
    public String addUser(@Valid @RequestBody RegistrationForm registrationForm) {
        return userService.addUser(registrationForm);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/{id}")
    public String updateUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result) {
        String res = userService.updateUser(user);
        if (res.equals("NOT OK")) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(x -> errors.append(x.getDefaultMessage()).append("\n"));
            return errors.toString();
        }
        return res;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/{id}/orders")
    public String addNewOrder(@PathVariable Long id, @RequestBody List<ProductCountOnly> cart) {
        return orderService.addNewOrder(id, cart);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(value = "/{id}/orders")
    public String changeOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.changeOrder(order);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{id}/orders")
    public List<Order> getOrderHistory(@PathVariable Long id) {
        return orderService.getOrderHistory(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @ExceptionHandler
    public ResponseEntity<User> handleXXException(BadCredentialsException e) {
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
}
