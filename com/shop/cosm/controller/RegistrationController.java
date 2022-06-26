package com.shop.cosm.controller;

import com.shop.cosm.domain.ImageFinger;
import com.shop.cosm.domain.Role;
import com.shop.cosm.domain.User;
import com.shop.cosm.recognize.RecognizeRepo;
import com.shop.cosm.repos.ImageFingerRepo;
import com.shop.cosm.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ImageFingerRepo imageFingerRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/registration/finger")
    public String registrationFinger() {
        return "finger";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("file1") MultipartFile file1 , User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
    @PostMapping("/registration/finger")
    public String addUserByFinger(@RequestParam("file1") MultipartFile file1, User user, Map<String, Object> model) throws IOException {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        RecognizeRepo recognizeRepo = new RecognizeRepo();
        recognizeRepo.add(file1.getBytes(),file1.getOriginalFilename(),"jpeg");
        userRepo.save(user);
        ImageFinger imageFinger = new ImageFinger(user,file1.getOriginalFilename());
        imageFingerRepo.save(imageFinger);
        return "redirect:/login";
    }
}
