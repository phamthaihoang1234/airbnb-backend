package com.codegym.airbnb.controller;


import com.codegym.airbnb.model.Response;
import com.codegym.airbnb.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/country")
@CrossOrigin("*")
public class CountryController {
    @Autowired
    CountryService countryService;

    Response res = new Response();

    @GetMapping
    public Response getAll(){
        res.setData(countryService.getAll());
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }

    @GetMapping("/{id}")
    public Response getOne(@PathVariable int id){
        res.setData(countryService.getOne(id));
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }
}
