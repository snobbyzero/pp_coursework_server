package com.example.pp_coursework_server.controller;

import com.example.pp_coursework_server.entity.City;
import com.example.pp_coursework_server.service.CityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public List<City> getCities() {
        return cityService.getCities();
    }
}
