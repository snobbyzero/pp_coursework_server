package com.example.pp_coursework_server.controller;

import com.example.pp_coursework_server.entity.Category;
import com.example.pp_coursework_server.service.AccountingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounting")
public class AccountingController {

    final AccountingService accountingService;

    public AccountingController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    @GetMapping("/profit")
    public Long getCategoryProfit(@RequestParam String categoryName) {
        return accountingService.getCategoryProfit(categoryName);
    }

    @GetMapping("/weight")
    public Double getAverageTotalWeight() {
        return accountingService.getAverageTotalWeight();
    }

    @GetMapping("price")
    public Double getAverageTotalPrice() {
        return accountingService.getAverageTotalPrice();
    }
}
