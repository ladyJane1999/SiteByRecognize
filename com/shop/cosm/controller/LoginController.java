package com.shop.cosm.controller;

import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintImageOptions;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import com.microsoft.azure.cognitiveservices.vision.faceapi.FaceAPI;
import com.microsoft.azure.cognitiveservices.vision.faceapi.models.AzureRegions;
import com.shop.cosm.domain.ImageFinger;
import com.shop.cosm.domain.Role;
import com.shop.cosm.library.*;
import com.shop.cosm.recognize.RecognizeRepo;
import com.shop.cosm.repos.ImageFingerRepo;
import com.shop.cosm.repos.ImageRepoFace;
import com.shop.cosm.repos.UserRepo;
import com.shop.cosm.security.JWTTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/login")
public class LoginController {

    final String key = "d3c9172caca7432bb5ddb72dab57ae38";
    final AzureRegions myRegion = AzureRegions.EASTUS;
    final String PERSON_GROUP_ID = "new_key3337774";

    private final UserRepo userRepo;

    private final JWTTokenProvider jwtTokenProvider;

    private final ImageRepoFace imageRepoFace;

    private final ImageFingerRepo imageRepoFingerRepo;

    public LoginController(UserRepo userRepo, JWTTokenProvider jwtTokenProvider, ImageRepoFace imageRepoFace, ImageFingerRepo imageRepoFingerRepo) {
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
        this.imageRepoFace = imageRepoFace;

        this.imageRepoFingerRepo = imageRepoFingerRepo;
    }

    @PostMapping("/by_face")
    public ResponseEntity login_by_photo_(@RequestParam("file1") MultipartFile file1) throws IOException {
        FaceAPI client = FaceAPIManager.authenticate(myRegion, key);
        RecognizeAPIBuilderFactory factory = new RecognizeAPIBuilderFactory(new RecognizeRepo(), client, PERSON_GROUP_ID);
        ISettingRecognize recognizer = factory.GetBuilder(RecognizerwithAzure.class);
        recognizer.train();
        String names = recognizer.recognize(file1.getBytes(), 90);
        return  ResponseEntity.ok(userRepo.findByUsername(names));
    }

    @PostMapping(value="/by_figprint", headers = "Content-Type= multipart/form-data")
    public ResponseEntity login_by_figprint_(@RequestParam MultipartFile file) throws IOException {

        FingerprintTemplate probe = new FingerprintTemplate(
                new FingerprintImage(
                        file.getBytes(),
                        new FingerprintImageOptions()
                                .dpi(500)));
        FingerprintMatcher matcher = new FingerprintMatcher(probe);
        double high = 0;
        ImageFinger match = null;

        for (ImageFinger candidate:imageRepoFingerRepo.findAll()) {
            FingerprintTemplate template = new FingerprintTemplate(
                    new FingerprintImage(
                            Files.readAllBytes(Paths.get(candidate.getFilename())),
                            new FingerprintImageOptions()
                                    .dpi(500)));
            double score = matcher.match(template);
            if (score > high) {
                high = score;
                match = candidate;
            }
        }
        double threshold = 10;

        if(high >= threshold) {
            String token = jwtTokenProvider.createToken(match.getAuthor().getUsername(), Role.USER.name()
            );
            Map<Object, Object> response = new HashMap<>();
            response.put("token",token);
            response.put("user",match.getAuthor());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JWTTokenProvider.AUTHORIZATION_HEADER, "Bearer " + response.get("token"));
            return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
        }

        return  ResponseEntity.badRequest().body("Not found");
    }

}
