package com.codegym.airbnb.services.impl;

import com.codegym.airbnb.model.Room;
import com.codegym.airbnb.repositories.HomeRepository;
import com.codegym.airbnb.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private HomeRepository homeRepository;

    @Override
    public Iterable<Room> findAll() {
        return homeRepository.findAll();
    }

    @Override
    public Iterable<Room> findAllByProvinceId(int id) {
        return homeRepository.findAllRoomByProvinceId(id);
    }

    @Override
    public Iterable<Room> findAllByAddress(String add) {
        return homeRepository.findAllRoomByAddress(add);
    }

    @Override
    public Iterable<Room> findAllCustomQuery() {
        return homeRepository.findAllCustomQuery();
    }

    @Override
    public Optional<Room> findById(Long id) {
        return homeRepository.findById(id);
    }

    @Override
    public Optional<Room> findByHomeId(Long id) {
        return homeRepository.findByHomeId(id);
    }

    @Override
    public Room save(Room room) {
        return homeRepository.save(room);
    }

    @Override
    public Optional<Room> deleteById(Long id) {
        Optional<Room> home = homeRepository.findById(id);
        homeRepository.deleteById(id);
        return home;
    }
}
