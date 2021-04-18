package com.codegym.airbnb.controller;

import com.codegym.airbnb.model.Response;
import com.codegym.airbnb.services.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/propertyType")
@CrossOrigin("*")
public class PropertyTypeController {
    @Autowired
    PropertyTypeService propertyTypeService;
    Response res = new Response();

    @GetMapping
    public Response getAll(){
        res.setData(propertyTypeService.getAll());
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }

    @GetMapping("/{id}")
    public Response getOne(@PathVariable Long id){
        res.setData(propertyTypeService.getOne(id));
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }
}
