package com.codegym.airbnb.services.impl;

import com.codegym.airbnb.model.Province;
import com.codegym.airbnb.repositories.ProvinceRepository;
import com.codegym.airbnb.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public Iterable<Province> findAll() {
        return provinceRepository.findAll();
    }

    @Override
    public Optional<Province> findById(Long id) {
        return provinceRepository.findById(id);
    }

    @Override
    public Province save(Province province) {
        return provinceRepository.save(province);
    }

    @Override
    public void delete(Long id) {
        provinceRepository.deleteById(id);
    }
}
