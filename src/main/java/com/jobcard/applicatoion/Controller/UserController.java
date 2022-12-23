package com.jobcard.applicatoion.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.WriterException;
import com.jobcard.applicatoion.Entity.Image;
import com.jobcard.applicatoion.Entity.User;
import com.jobcard.applicatoion.Entity.UserQr;
import com.jobcard.applicatoion.Service.IImageService;
import com.jobcard.applicatoion.Service.IQRCodeService;
import com.jobcard.applicatoion.Service.IUserService;
import com.jobcard.applicatoion.mappers.UserMapper;
import com.jobcard.applicatoion.util.ImageUtility;

import java.io.IOException;

// @CrossOrigin(origins = "http://localhost:8082") open for specific port
@CrossOrigin() // open for all ports
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final int WIDTH = 250;
    private final int HEIGHT = 250;

    @Autowired
    private IUserService userService;
    @Autowired
    private IImageService imageService;
    @Autowired
    private IQRCodeService qrCodeService;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<byte[]> createUser(@RequestPart("user") User user, @RequestPart("image") MultipartFile file) {
        try {

            Image imageUser = uploadImage(file);
            System.out.println(user);
            user.setImage(imageUser);
            userService.createUser(user);
            byte[] rquser = generateUserQr(user);

            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(rquser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public Image uploadImage(MultipartFile file) throws IOException {
        Image image = imageService.uploadeImage(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes())).build());
        return image;
    }

    public byte[] generateUserQr(User user) throws WriterException, IOException {
        UserQr UserQr = UserMapper.userToUserQR(user);

        byte[] qrCode = qrCodeService.getQRCodeImage(UserQr.toString(), WIDTH, HEIGHT);
        return qrCode;
    }

}
