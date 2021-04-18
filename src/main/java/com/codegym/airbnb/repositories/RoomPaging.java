package com.codegym.airbnb.repositories;

import com.codegym.airbnb.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoomPaging extends PagingAndSortingRepository<Room, Long> {
    Page<Room> findAllByUserId(long user_id, Pageable pageable);
}
