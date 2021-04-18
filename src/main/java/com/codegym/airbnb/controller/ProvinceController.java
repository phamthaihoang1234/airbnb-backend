package com.codegym.airbnb.controller;

import com.codegym.airbnb.model.Province;
import com.codegym.airbnb.model.Response;
import com.codegym.airbnb.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/province")
@CrossOrigin("*")
public class ProvinceController {
    @Autowired
    ProvinceService provinceService;

    @GetMapping
    public Response findAll() {
        return new Response(provinceService.findAll(), "success", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable Long id) {
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()) {
            return new Response(province.get(), "success", HttpStatus.OK);
        } else {
            return new Response(null, "Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
