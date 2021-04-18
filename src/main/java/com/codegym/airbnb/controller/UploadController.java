package com.codegym.airbnb.controller;

import com.codegym.airbnb.model.Response;
import com.codegym.airbnb.model.Room;
import com.codegym.airbnb.model.RoomImage;
import com.codegym.airbnb.repositories.HomeRepository;
import com.codegym.airbnb.services.RoomImageService;
import com.codegym.airbnb.storage.StorageException;
import com.codegym.airbnb.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class UploadController {

    @Autowired
    private StorageService storageService;
    @Autowired
    private RoomImageService roomImageService;
    @Autowired
    private HomeRepository homeRepository;

    private Logger logger = LoggerFactory.getLogger(UploadController.class);

    @PostMapping("upload2")
    public Response uploadFileToFolder(@RequestParam("file") MultipartFile file) {
        RoomImage roomImage = new RoomImage();
        Room room = homeRepository.GetLastRoom();
        try {
            storageService.store(file);
            logger.info("ANHNBT: " + file.getOriginalFilename());
            roomImage.setImageUrl(file.getOriginalFilename());
            roomImage.setRoom(room);
            roomImageService.save(roomImage);
        } catch (StorageException e) {
            roomImage.setImageUrl("150.png");
            logger.warn("ANHNBT-EXCEPTION: ", e);
        }
        return new Response(roomImage, "success", HttpStatus.OK);
    }

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Response uploadImages(@RequestParam("image") String image, @RequestParam("imageSource") MultipartFile[] files) {
        System.out.println("File length: " + files.length);
        try {
            for (MultipartFile file : files) {
                logger.info("ANHNBT: " + file.getOriginalFilename());
                storageService.store(file);
//                roomImage.setImageUrl(file.getOriginalFilename());

            }
        } catch (StorageException e) {
//            roomImage.setImageUrl("150.png");
            logger.warn("ANHNBT-EXCEPTION: ", e);
        }
        return new Response(image, "success", HttpStatus.OK);
    }

    @GetMapping("files")
    public Response listUploadedFiles() {

//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(UploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toUri().toString())
//                .collect(Collectors.toList()));

        return new Response(storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(UploadController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()), "success", HttpStatus.OK);
    }
}
