package com.shop.cosm.library;

import com.microsoft.azure.cognitiveservices.vision.faceapi.FaceAPI;

public class RecognizeAPIBuilderFactory {
    private IRecognizeRepo repo;
    private FaceAPI client;
    private String personGroupId;


    public RecognizeAPIBuilderFactory(
            IRecognizeRepo repo,
            FaceAPI client,
            String personGroupId)
    {
        this.repo = repo;
        this.client = client;
        this.personGroupId = personGroupId;
    }

    public ISettingRecognize GetBuilder(Class tClass){

        switch(tClass.getSimpleName()){

            case "RecognizerwithAzure":
                return new RecognizerwithAzure(repo, client, personGroupId);

            case "RecognizerwithOpencv":
                return new RecognizerwithOpencv(repo);

            default:
                throw new UnsupportedOperationException("Unsupported type of T");
        }
    }
}
