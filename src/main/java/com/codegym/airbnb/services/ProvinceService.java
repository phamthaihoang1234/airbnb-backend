package com.codegym.airbnb.services;

import com.codegym.airbnb.model.Province;

import java.util.Optional;

public interface ProvinceService {

    Iterable<Province> findAll();

    Province save(Province province);

    void delete(Long id);

    Optional<Province> findById(Long id);
}
