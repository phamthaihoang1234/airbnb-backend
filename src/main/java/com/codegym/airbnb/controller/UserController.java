package com.codegym.airbnb.controller;

import com.codegym.airbnb.model.*;
import com.codegym.airbnb.repositories.BookingRepository;
import com.codegym.airbnb.repositories.RoomPaging;
import com.codegym.airbnb.services.HomeService;
import com.codegym.airbnb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HomeService homeService;
    @Autowired
    private BookingRepository bookingService;
    @Autowired
    private RoomPaging roomPaging;

    private final Response res = new Response();

    @GetMapping
    public Response getAll() {
        res.setData(userService.findAll());
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }

    @GetMapping("/{id}")
    public Response getOne(@PathVariable Long id) {
        res.setData(userService.findById(id));
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }

    @GetMapping("edit-user/{un}")
    public Response getOneByUsername(@PathVariable String un) {
        res.setData(userService.findByUserName(un));
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }

    @PutMapping("{id}")
    public Response updatePassword(@RequestBody LoginForm loginForm, @PathVariable Long id) {
        Optional<UserModel> userModel = userService.findById(id);
        if (userModel.isPresent()) {
            userModel.get().setPassword(loginForm.getPassword());
            userService.save(userModel.get());
            res.setData(userModel);
            res.setMessage("success");
            res.setStatus(HttpStatus.OK);
        } else {
            res.setMessage("No user available");
            res.setStatus(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @PostMapping("{id}")
    public Response update(@RequestBody UserModel user, @PathVariable("id") Long id) {
        Optional<UserModel> userModel = userService.findById(id);
        if (userModel.isPresent()) {
            userModel.get().setName(user.getName());
            userModel.get().setDateOfBirth(user.getDateOfBirth());
            userModel.get().setEmail(user.getEmail());
            userModel.get().setPhone(user.getPhone());
            userModel.get().setModifiedDate(LocalDateTime.now());
            res.setData(userService.save(userModel.get()));
            res.setMessage("success");
            res.setStatus(HttpStatus.OK);
        } else {
            res.setData(null);
            res.setMessage("Not Found");
            res.setStatus(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @PostMapping("/edit")
    public Response editUser(@RequestBody UserModel user) {
        userService.save(user);
        res.setData(user);
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }

    @GetMapping("/{id}/rooms")
    public Response getRooms(@PathVariable long id,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "4") int size) {
        List<Room> roomList = new ArrayList<>();
        PageRequest paging = PageRequest.of(page, size);
        Page<Room> pageRooms;

        pageRooms = roomPaging.findAllByUserId(id, paging);

        for (Room room : homeService.findAll()) {
            if (room.getUser().getId() == id) {
                roomList.add(room);
            }
        }
        res.setData(pageRooms);
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }

    @GetMapping("/{id}/bookings")
    public Response getBookings(@PathVariable long id) {
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : bookingService.findAll()) {
            if (booking.getUser().getId() == id) {
                bookings.add(booking);
            }
        }
        res.setData(bookings);
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }
}
