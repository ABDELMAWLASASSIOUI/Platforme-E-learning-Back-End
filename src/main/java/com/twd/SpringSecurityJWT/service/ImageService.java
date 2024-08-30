package com.twd.SpringSecurityJWT.service;

import com.twd.SpringSecurityJWT.entity.Image;
import com.twd.SpringSecurityJWT.repository.ImageRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    public Image saveImage( MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        logger.info("Uploading file: " + fileName);

        Image image = new Image();
        image.setName(fileName);
        image.setType(file.getContentType());
        image.setData(file.getBytes());  // Le data est bien défini ici

        logger.info("File data length: " + image.getData().length);
        return imageRepository.save(image);
    }


    public Image getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}

