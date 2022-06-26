package com.shop.cosm.service;

import com.shop.cosm.domain.Role;
import com.shop.cosm.domain.User;
import com.shop.cosm.repos.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Map;

@Service
public class RegistrationService {

    private final UserRepo userRepo;

    public RegistrationService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
    public String valideAdd(MultipartFile file1, User user, Map<String, Object> model)
    {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "login";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return  "registration";
    }
}
