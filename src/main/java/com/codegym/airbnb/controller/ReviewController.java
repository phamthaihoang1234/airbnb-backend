package com.codegym.airbnb.controller;

import com.codegym.airbnb.model.Booking;
import com.codegym.airbnb.model.Response;
import com.codegym.airbnb.model.Review;
import com.codegym.airbnb.services.BookingService;
import com.codegym.airbnb.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/review")
@CrossOrigin("*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BookingService bookingService;
    Response res = new Response();

    @GetMapping
    public Response findAll() {
        res.setData(reviewService.findAll());
        res.setStatus(HttpStatus.OK);
        res.setMessage("success");
        return res;
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable Long id) {
        res.setData(reviewService.findById(id).get());
        res.setStatus(HttpStatus.OK);
        res.setMessage("success");
        return res;
    }

    @GetMapping("/room/{id}")
    public Response findByRoomIdQuery(@PathVariable Long id) {
        res.setData(reviewService.findByRoomIdQuery(id));
        res.setStatus(HttpStatus.OK);
        res.setMessage("success");
        return res;
    }

    @GetMapping("/avg-ratting/{id}")
    public Response findAvgRattingByRoomIdQuery(@PathVariable Long id) {
        res.setData(reviewService.findAvgByRoomIdQuery(id));
        res.setStatus(HttpStatus.OK);
        res.setMessage("success");
        return res;
    }

    @PostMapping
    public Response save(@RequestBody Review review) {
        Optional<Booking> booking = bookingService.findById(review.getBooking().getId());
        if (booking.isPresent()) {
            Review newReview = new Review();
            newReview.setRating(review.getRating());
            newReview.setReviewBody(review.getReviewBody());
            newReview.setBooking(booking.get());
            newReview.setActive(true);
            newReview.setCreatedDate(LocalDateTime.now());
            newReview.setModifiedDate(LocalDateTime.now());
            Review reviewSave = reviewService.save(newReview);
            res.setData(reviewSave);
            res.setStatus(HttpStatus.OK);
            res.setMessage("success");
        } else {
            res.setData(null);
            res.setStatus(HttpStatus.NOT_FOUND);
            res.setMessage("Booking not found");
        }
        return res;
    }

}
