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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;;

// @CrossOrigin(origins = "http://localhost:8082") open for specific port
//@CrossOrigin() // open for all ports
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
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
    public ResponseEntity<Map<String, Object>> createUser(@RequestPart("user") User user,
            @RequestPart("image") MultipartFile file) {
        try {

            // image coding
            Image imageUser = uploadImage(file);
            // set the image to the user
            user.setImage(imageUser);
            // save user and generat the qr code
            User newUser = userService.createUser(user);

            byte[] rquser = generateUserQr(user);
            String qrcode = Base64.getEncoder().encodeToString(rquser);
            // return the user creted and his qr code
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("data", qrcode);
            data.put("user", userTouser(newUser));
            return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private User userTouser(User newUser) {
        Image dbImage = newUser.getImage();
        newUser.setImage(Image.builder() 
        .name(dbImage.getName())
        .type(dbImage.getType())
        .image(ImageUtility.decompressImage(dbImage.getImage())).build());

        return newUser;
        
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
        return imageService.uploadeImage(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtility.compressImage(file.getBytes())).build());

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
