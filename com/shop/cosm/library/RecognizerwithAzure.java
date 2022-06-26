package com.shop.cosm.library;

import com.microsoft.azure.cognitiveservices.vision.faceapi.FaceAPI;
import com.microsoft.azure.cognitiveservices.vision.faceapi.models.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class RecognizerwithAzure implements ISettingRecognize {

    private IRecognizeRepo recognizeRepo;
 private String personGroupID;
//    private IRecognizewithAzure iRecognizewithAzure;
   private AzureRegions myRegion = AzureRegions.EASTUS;
   private FaceAPI client;
//    FaceAPI client = FaceAPIManager.authenticate(myRegion, key);
    public RecognizerwithAzure(IRecognizeRepo recognizeRepo,FaceAPI client,String personGroupID) {
        this.recognizeRepo = recognizeRepo;
        this.client = client;
        this.personGroupID=personGroupID;
    }


    public void train() throws IOException {
//        FaceAPI client = FaceAPIManager.authenticate(myRegion, key);

        String[] imageFiles = recognizeRepo.readAll("D:/Person");

        // A group photo that includes some of the persons you seek to identify from your dictionary.
        System.out.println("Creating the person group " + personGroupID + " ...");
        // Create the person group, so our photos have one to belong to.
//        client.personGroups().createPersonGroup(personGroupID, new CreatePersonGroupOptionalParameter().withName(personGroupID));
        client.personGroups().get(personGroupID);
        for (String personName : imageFiles) {
            byte[] bytes = recognizeRepo.read(personName);
            Person person = client.personGroupPersons().createPerson(personGroupID,
                    new CreatePersonOptionalParameter().withName(personName));

            client.personGroupPersons().addPersonFaceFromStream(personGroupID, person.personId(), bytes, null);
//                    client.personGroupPersons().addPersonFaceFromUrl(personGroupID, person.personId(), imageBaseURL + personImage, null);

        }
        try {
            Thread.sleep(1000 * 60);
        } catch (InterruptedException e) {

        }
        System.out.println();
        System.out.println("Training person group " + personGroupID + " ...");
        client.personGroups().train(personGroupID);
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Get training status
            TrainingStatus status = client.personGroups().getTrainingStatus(personGroupID);

            if (status.status() == TrainingStatusType.SUCCEEDED) {
                System.out.println("Training status: " + status.status());
                break;
            }
            System.out.println("Training status: " + status.status());
        }
        System.out.println();
    }

    public String recognize( byte[] uploadPhoto,double confidenceValue) throws IOException {

        List<DetectedFace> facesList = client.faces().detectWithStream(uploadPhoto, new DetectWithStreamOptionalParameter().withReturnFaceId(true));

        List<UUID> detectedFaces = new ArrayList<>();
        for (DetectedFace face : facesList) {
            detectedFaces.add(face.faceId());
        }
        // Detect faces from our group photo (which may contain one of our person group persons)
//             Identifies which faces in group photo are in our person group.


        String names=null;
        List<IdentifyResult> identifyResults = client.faces().identify(personGroupID, detectedFaces, null);
        for (IdentifyResult person : identifyResults) {
            Person p = client.personGroupPersons().get(personGroupID, person.candidates().get(0).personId());
            person.candidates().get(0).confidence();
            Path path1 = Paths.get(p.name());
            names = path1.getFileName().toString();
//            predictLabel = Integer.parseInt(name.split("\\-")[0]);
        }
        return names;
    }
}



