package com.codegym.airbnb.services;

import com.codegym.airbnb.model.Room;

import java.util.Optional;

public interface HomeService {

    Iterable<Room> findAll();

    Iterable<Room> findAllByProvinceId(int id);

    Iterable<Room> findAllByAddress(String add);

    Iterable<Room> findAllCustomQuery();

    Optional<Room> findById(Long id);

    Optional<Room> findByHomeId(Long id);

    Room save(Room room);

    Optional<Room> deleteById(Long id);

}
