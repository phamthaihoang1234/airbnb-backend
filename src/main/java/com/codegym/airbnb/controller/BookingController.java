package com.codegym.airbnb.controller;

import com.codegym.airbnb.exception.BookingNotFoundException;
import com.codegym.airbnb.exception.RoomNotFoundException;
import com.codegym.airbnb.exception.UserNotFoundException;
import com.codegym.airbnb.model.*;
import com.codegym.airbnb.repositories.BookingOfCus;
import com.codegym.airbnb.services.BookingService;
import com.codegym.airbnb.services.HomeService;
import com.codegym.airbnb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/bookings")
@CrossOrigin("*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private HomeService homeService;

    @Autowired
    private BookingOfCus bookingOfCus;

    @GetMapping
    public Iterable<Booking> all() {
        return bookingService.findAll();
    }

    @GetMapping("{id}")
    public Booking one(@PathVariable("id") Long id) {
        return bookingService.findById(id).orElseThrow(() -> new BookingNotFoundException(id));
    }

    @PostMapping(value = "{roomId}/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response save(
            @PathVariable("roomId") Long roomId,
            @PathVariable("userId") Long userId,
            @RequestBody Booking booking) {
        UserModel user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Room room = homeService.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        Optional<Booking> oldBooking = bookingService.findByStartDateAndUserIdAndRoomId(userId, roomId, booking.getStartDate());
        if (oldBooking.isPresent()) {
            return new Response(null, "Bạn chỉ có thể đặt phòng này một lần trong cùng 1 ngày!", HttpStatus.CONFLICT);
        }
        booking.setStatus(BookingStatus.IN_PROGRESS);
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCreatedDate(LocalDateTime.now());
        booking.setModifiedDate(LocalDateTime.now());
        return new Response(bookingService.save(booking), "success", HttpStatus.OK);
    }

    @PutMapping("{id}/cancelled")
    public Response cancelled(@PathVariable("id") Long id) {
        Booking booking = bookingService.findById(id).orElseThrow(() -> new BookingNotFoundException(id));
        Response response = new Response();
        booking.setStatus(BookingStatus.CANCELLED);
        Booking newBooking = bookingService.save(booking);
        response.setData(newBooking);
        response.setMessage("success");
        response.setStatus(HttpStatus.OK);
        return response;
    }

    @GetMapping("user/{id}")
    public Response getBookingsOfCus(@PathVariable("id") Long id) {
        Response response = new Response();
        response.setData(bookingOfCus.getBookingsOfCus(id));
        response.setMessage("success");
        response.setStatus(HttpStatus.OK);
        return response;
    }

    @GetMapping("{room_id}/{user_id}")
    public Response getBookingsOfCus(@PathVariable Long room_id, @PathVariable Long user_id) {
        Response response = new Response();
        response.setData(bookingService.findByRoomIdAndUserId(room_id, user_id));
        response.setMessage("success");
        response.setStatus(HttpStatus.OK);
        return response;
    }
}
