package com.codegym.airbnb.controller;

import com.codegym.airbnb.model.Response;
import com.codegym.airbnb.model.Room;
import com.codegym.airbnb.services.HomeService;

import com.codegym.airbnb.services.ReviewService;
import com.codegym.airbnb.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/rooms")
@CrossOrigin("*")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private ReviewService reviewService;


    @GetMapping
    public Response home() {
        Response res = new Response();
        Iterable<Room> rooms = homeService.findAll();
        for (Room room : rooms) {
            room.setAvgRatting(reviewService.findAvgByRoomIdQuery(room.getId()));
        }
        res.setData(rooms);
        // Fix lai trả về dữ liệu có phân trang.
        res.setMessage("SUCCESS");
        res.setStatus(HttpStatus.OK);
        return res;
    }

    @GetMapping("{id}")
    public Response findById(@PathVariable("id") Long id) {
        Response res = new Response();
        Optional<Room> home = homeService.findById(id);
        res.setData(home.orElseGet(() -> null));
        if (home.isPresent()) {
            res.setMessage("SUCCESS");
            res.setStatus(HttpStatus.OK);
        } else {
            res.setMessage("No rooms available");
            res.setStatus(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    @GetMapping("/province/{id}")
    public Response findByProvinceId(@PathVariable("id") int id) {
        Response res = new Response();
        ArrayList<Room> home = (ArrayList<Room>) homeService.findAllByProvinceId(id);
        res.setMessage("SUCCESS");
        res.setStatus(HttpStatus.OK);
        res.setData(home);
        return res;
    }

    @GetMapping("/address/{add}")
    public Response findByAddress(@PathVariable("add") String add) {
        Response res = new Response();
        ArrayList<Room> home = (ArrayList<Room>) homeService.findAllByAddress(add);
        res.setMessage("SUCCESS");
        res.setStatus(HttpStatus.OK);
        res.setData(home);
        return res;
    }

    @PutMapping("{id}/cancelled")
    public Response cancelled(@PathVariable("id") Long id, @RequestBody Room roomObj) {
        Optional<Room> room = homeService.findById(id);
        if (room.isPresent()) {
            room.get().setCancelled(roomObj.getCancelled());
            homeService.save(room.get());
            return new Response(room, "success", HttpStatus.OK);
        } else {
            return new Response(null, "Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
