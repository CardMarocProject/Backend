package com.jobcard.applicatoion.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jobcard.applicatoion.Entity.Image;
import com.jobcard.applicatoion.Service.IImageService;
import com.jobcard.applicatoion.util.ImageUtility;

//@CrossOrigin(origins = "http://localhost:4200") // open for specific port
// @CrossOrigin() // open for all ports
@CrossOrigin(origins = "*", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
@RestController
@RequestMapping("api/v1/image")
public class ImageController {

    @Autowired
    private IImageService imageService;

    @GetMapping(path = { "{id}" })
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {
        try {
            Optional<Image> dbImage = isExistImage(id);
            if (dbImage != null) {
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.valueOf(dbImage.get().getType()))
                        .body(ImageUtility.decompressImage(dbImage.get().getImage()));

            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Method to search the image by id
     *
     * @param id
     * @return image
     */
    private Optional<Image> isExistImage(Long id) {
        Optional<Image> img = imageService.findById(id);

        if (img.isPresent()) {
            return img;
        }
        return null;
    }

}
