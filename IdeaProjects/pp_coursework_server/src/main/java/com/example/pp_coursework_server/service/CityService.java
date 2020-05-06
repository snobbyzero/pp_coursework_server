package com.example.pp_coursework_server.service;

import com.example.pp_coursework_server.entity.City;
import com.example.pp_coursework_server.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getCities() {
        return cityRepository.findAll();
    }
}
