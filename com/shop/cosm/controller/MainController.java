package com.shop.cosm.controller;
import com.microsoft.azure.cognitiveservices.vision.faceapi.FaceAPI;
import com.microsoft.azure.cognitiveservices.vision.faceapi.models.AzureRegions;
import com.shop.cosm.domain.ImageFinger;
import com.shop.cosm.domain.User;
import com.shop.cosm.library.*;
import com.shop.cosm.recognize.RecognizeRepo;
import com.shop.cosm.repos.ImageFingerRepo;
import com.shop.cosm.repos.ImageRepoFace;
import com.shop.cosm.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintImageOptions;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;

@Controller
public class MainController {
    final String key = "d3c9172caca7432bb5ddb72dab57ae38";
    final AzureRegions myRegion = AzureRegions.EASTUS;
    final String PERSON_GROUP_ID = "new_key3337774";
    private RecognizerwithAzure recognizerwithAzure;
    RecognizerwithOpencv recognizerwithOpencv = new RecognizerwithOpencv(new RecognizeRepo());

    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private ImageRepoFace imageRepoFace;
    @Autowired
    private ImageFingerRepo imageRepoFingerRepo;


    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/apps")
    public String apps() {
        return "apps";
    }

    @GetMapping("/about")
    public String about() {

        return "about";
    }

    @GetMapping("/lll")
    public String webcam() {

        return "webcam";
    }

    @GetMapping("/l")
    public String login_by_photo() {
        return "login_by_photo";
    }

    @PostMapping("/l")
    public String login_by_photo_(@RequestParam("file1") MultipartFile file1, Map<String, Object> model) throws IOException {
        FaceAPI client = FaceAPIManager.authenticate(myRegion, key);
        RecognizeAPIBuilderFactory factory = new RecognizeAPIBuilderFactory(new RecognizeRepo(), client, PERSON_GROUP_ID);
        ISettingRecognize recognizer = factory.GetBuilder(RecognizerwithAzure.class);
        recognizer.train();
        String names = recognizer.recognize(file1.getBytes(), 90);
        User user = userRepo.findByUsername(names);
        model.put("name",user);
        return "redirect:/";
    }

    @GetMapping("/logisn_by_figprint")
    public String login_by() {
        return "login_by";
    }

    @PostMapping("/logisn_by_figprint")
    public String login_by_figprint_(@RequestParam("file1") MultipartFile file, Map<String, Object> model) throws IOException {

        FingerprintTemplate probe = new FingerprintTemplate(
                new FingerprintImage(
                        file.getBytes(),
                        new FingerprintImageOptions()
                                .dpi(500)));
        FingerprintMatcher matcher = new FingerprintMatcher(probe);
        double high = 0;
        ImageFinger match = null;

        File files = new File("D:/Database");

        for (ImageFinger candidate: imageRepoFingerRepo.findAll()) {
            FingerprintTemplate template = new FingerprintTemplate(
                    new FingerprintImage(
//                            Files.readAllBytes( Paths.get(files.getAbsolutePath()+candidate.getFilename())),
                            Files.readAllBytes(Paths.get(candidate.getFilename())),
                            new FingerprintImageOptions()
                                    .dpi(500)));
            double score = matcher.match(template);
            if (score > high) {
                high = score;
                match = candidate;
            }
        }
        double threshold = 40;

        if(high >= threshold) {
            model.put("score", match.getAuthor());
        }

        return "login_by";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file
    ) throws IOException {
        RecognizeRepo recognizeRepo = new RecognizeRepo();
        RecognizerwithOpencv recognizerwithOpencv1 = new RecognizerwithOpencv(new RecognizeRepo());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename =  recognizeRepo.add(file.getBytes(),user.getUsername() ,extension);
//        ImageFace image = new ImageFace(user,filename);
//        imageRepo.save(new ImageFace(user,filename));
        recognizerwithOpencv1.train();
        return "main";
    }




    @PostMapping("delete")
    public String delete(@AuthenticationPrincipal User user,@RequestParam("filename") String fileid) {
        RecognizeRepo lib = new RecognizeRepo();
        lib.delete(fileid);
//        List<Image> image =  imageRepo.findByFilename(fileid);
//        imageRepo.deleteAll(image);
        return "main";
    }

}











