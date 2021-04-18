package com.codegym.airbnb.controller;


import com.codegym.airbnb.model.Response;
import com.codegym.airbnb.model.Room;
import com.codegym.airbnb.model.RoomImage;
import com.codegym.airbnb.repositories.HomeRepository;
import com.codegym.airbnb.services.RoomImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/images")
@CrossOrigin("*")
public class RoomImageController {
    @Autowired
    private RoomImageService roomImageService;
    @Autowired
    private HomeRepository homeRepository;
    Response res = new Response();

    @GetMapping
    private Response getAll(){
        res.setData(roomImageService.getAll());
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }

    @PostMapping
    private Response save(@RequestBody RoomImage roomImage){
        Room room = homeRepository.GetLastRoom();
        roomImage.setRoom(room);
        res.setData(roomImageService.save(roomImage));
        res.setStatus(HttpStatus.OK);
        res.setMessage("SUCCESS");
        return res;
    }
}
