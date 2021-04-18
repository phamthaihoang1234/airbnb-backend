package com.codegym.airbnb.controller;

import com.codegym.airbnb.model.Booking;
import com.codegym.airbnb.model.Response;
import com.codegym.airbnb.model.UserModel;
import com.codegym.airbnb.services.BookingService;
import com.codegym.airbnb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/checkout")
public class CheckoutController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Response checkout(@RequestBody Booking booking) {
//        booking.setCreatedAt(LocalDateTime.now());
//        booking.setUpdatedAt(LocalDateTime.now());
        Optional<UserModel> user = userService.findById(booking.getUser().getId());
        if (user.isPresent()) {
            booking.setUser(user.get());
        }
        // set nối object room
        Booking newBooking = bookingService.save(booking);
        return new Response(newBooking, "success", HttpStatus.OK);
    }

    @GetMapping
    public Response getBooking() {
        return new Response(new Booking(), "success", HttpStatus.OK);
    }

}
