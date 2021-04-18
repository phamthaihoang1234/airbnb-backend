package com.codegym.airbnb.repositories;

import com.codegym.airbnb.model.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Long> {

    @Query(value = "select bookings.* from bookings left join rooms on rooms.id = bookings.room_id left join users on bookings.user_id = users.id where bookings.room_id = ?1 and bookings.user_id = ?2 limit 1", nativeQuery = true)
    Optional<Booking> findByRoomIdAndUserId(Long room_id, Long id_user);

    Optional<Booking> findByRoomId(Long id);

    @Query(value = "select * from bookings where user_id = ?1 and room_id = ?2 and start_date = ?3 limit 1", nativeQuery = true)
    Optional<Booking> findByStartDateAndUserIdAndRoomId(Long userId, Long roomId, LocalDate date);
}
