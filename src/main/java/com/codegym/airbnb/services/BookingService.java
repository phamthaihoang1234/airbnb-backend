package com.codegym.airbnb.services;

import com.codegym.airbnb.model.Booking;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingService  {

    Iterable<Booking> findAll();

    Booking save(Booking booking);

    Optional<Booking> findById(Long id);

    Optional<Booking> findByRoomIdAndUserId(Long room_id, Long id_user);

    Optional<Booking> findByRoomId(Long id);

    Optional<Booking> findByStartDateAndUserIdAndRoomId(Long userId, Long roomId, LocalDate date);

}
