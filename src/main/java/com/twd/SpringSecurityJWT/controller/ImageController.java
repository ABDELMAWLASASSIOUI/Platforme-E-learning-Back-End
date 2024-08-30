package com.twd.SpringSecurityJWT.controller;

import com.twd.SpringSecurityJWT.entity.Image;
import com.twd.SpringSecurityJWT.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/auth/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty. Please upload a valid file.");
        }

        try {
            Image savedImage = imageService.saveImage(file);
            return ResponseEntity.ok("File uploaded successfully with ID: " + savedImage.getId());
        } catch (IOException e) {
            logger.error("Failed to upload file", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(image.getData());
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        return ResponseEntity.ok(images);
    }
}
