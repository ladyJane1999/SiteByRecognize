package com.shop.cosm.controller;

import com.shop.cosm.domain.ImageFace;
import com.shop.cosm.domain.ResponseModel;
import com.shop.cosm.domain.Role;
import com.shop.cosm.domain.User;
import com.shop.cosm.repos.ImageFingerRepo;
import com.shop.cosm.repos.ImageRepoFace;
import com.shop.cosm.repos.UserRepo;
import com.shop.cosm.security.JWTTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserRepo userRepo;

    private final ImageFingerRepo imageFingerRepo;

    private final ImageRepoFace imageRepoFace;

    private final JWTTokenProvider jwtTokenProvider;


    public RegisterController(UserRepo userRepo, ImageFingerRepo imageFingerRepo, ImageRepoFace imageRepoFace, JWTTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.imageFingerRepo = imageFingerRepo;
        this.imageRepoFace = imageRepoFace;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @PostMapping("/by-face")
    public ResponseEntity addUser(@RequestBody MultipartFile file1 , User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return ResponseEntity. badRequest().body("User exist");
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        ImageFace imageFace = new ImageFace(user,file1.getOriginalFilename());
        imageRepoFace.save(imageFace);
        return  ResponseEntity.ok(userRepo.save(user));

    }
    @PostMapping(value="/by_finger", headers = "Content-Type= multipart/form-data")
    public ResponseEntity addUserByFinger(@RequestBody ResponseModel user) {

       user.getFile();
        String token = jwtTokenProvider.createToken(user.getUsername(), Role.USER.name()
        );
        Map<Object, Object> response = new HashMap<>();
        response.put("token",token);
        response.put("user",user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTTokenProvider.AUTHORIZATION_HEADER, "Bearer " + response.get("token"));
        return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
    }

}
