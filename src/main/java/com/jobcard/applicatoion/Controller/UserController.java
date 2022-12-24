package com.jobcard.applicatoion.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.Base64;;

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
    public ResponseEntity<String> createUser(@RequestPart("user") User user, @RequestPart("image") MultipartFile file) {
        try {
            System.out.println(user);
            System.out.println(file);
            Image imageUser = uploadImage(file);

            user.setImage(imageUser);
            userService.createUser(user);
            byte[] rquser = generateUserQr(user);
            String qrcode = Base64.getEncoder().encodeToString(rquser);
            System.out.println("mee" + qrcode);
            return new ResponseEntity<>(qrcode, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("{cin}")
    public ResponseEntity<User> getUserByCin(@PathVariable("cin") String cin) {
        try {
            // check if User exist in database
            User user = getUser(cin);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method to upload the user image
     *
     * @param file -> user image
     * @return image
     */

    public Image uploadImage(MultipartFile file) throws IOException {
        Image image = imageService.uploadeImage(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes())).build());
        return image;
    }

    /**
     * Method to generate the user Qr code
     *
     * @param user
     * @return User Qr Code
     */

    public byte[] generateUserQr(User user) throws WriterException, IOException {
        UserQr UserQr = UserMapper.userToUserQR(user);

        byte[] qrCode = qrCodeService.getQRCodeImage(UserQr.toString(), WIDTH, HEIGHT);
        return qrCode;
    }

    /**
     * Method to get the user by cin
     *
     * @param cin
     * @return User
     */
    private User getUser(String cin) {
        User userObj = userService.findByCin(cin);

        if (userObj != null) {
            return userObj;
        }
        return null;
    }

}
