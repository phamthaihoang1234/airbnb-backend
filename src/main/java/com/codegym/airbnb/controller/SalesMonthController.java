package com.codegym.airbnb.controller;


import com.codegym.airbnb.model.Booking;
import com.codegym.airbnb.model.BookingStatus;
import com.codegym.airbnb.model.Response;
import com.codegym.airbnb.repositories.BookingOfCus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@RestController
@CrossOrigin("*")
public class SalesMonthController {
    @Autowired
    private BookingOfCus bookingOfCus;

    Response res = new Response();

    @GetMapping(value = {"api/sales/{id}/{year}"})
    public Response getSalesMonth(@PathVariable Long id, @PathVariable(required = false) int year) {
        double saleOfMonth = 0;
        List<Booking> bookings = new ArrayList();
        List sales = new ArrayList();

        for (Booking booking : bookingOfCus.getBookingsOfCus(id)) {
            if (booking.getStatus() == BookingStatus.COMPLETED && booking.getCreatedDate().getYear() == year) {
                bookings.add(booking);
            }
        }
        for (int i = 1; i <= 12; i++) {
            for (Booking b : bookings) {
                if (b.getCreatedDate().getMonthValue() == i) {
                    saleOfMonth += b.getPrice();
                }
            }
            sales.add(saleOfMonth);
            saleOfMonth = 0;
        }
        res.setData(sales);

        return res;
    }
}
