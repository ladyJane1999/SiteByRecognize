package com.shop.cosm.controller;

import com.shop.cosm.domain.ImageFinger;
import com.shop.cosm.recognize.RecognizeRepo;
import com.shop.cosm.repos.ImageFingerRepo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserAccountController {

    private  final ImageFingerRepo imageFingerRepo;

    public UserAccountController(ImageFingerRepo imageFingerRepo) {
        this.imageFingerRepo = imageFingerRepo;
    }

    @PostMapping
    public ResponseEntity<ImageFinger> add(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file
    ) throws IOException {
        ImageFinger imageFinger = new ImageFinger(user,file.getOriginalFilename());

        return ResponseEntity.ok( imageFingerRepo.save(imageFinger)) ;
    }

    @DeleteMapping
    @PreAuthorize("USER")
    public String delete(@RequestParam("filename") String fileid) {
        RecognizeRepo lib = new RecognizeRepo();
        lib.delete(fileid);
        return "main";
    }

}
