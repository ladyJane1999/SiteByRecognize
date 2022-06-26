package com.shop.cosm.library;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.FisherFaceRecognizer;

import java.io.IOException;
import java.nio.IntBuffer;

import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.opencv.core.CvType.CV_32SC1;


public class
RecognizerwithOpencv implements ISettingRecognize {

    private IRecognizeRepo recognizeRepo;
    private String[] imageFiles;
    public  Integer integer;

    public RecognizerwithOpencv(IRecognizeRepo recognizeRepo){
        this.recognizeRepo = recognizeRepo;
    }


    public void train() throws IOException {
        imageFiles = recognizeRepo.readAll("D:/cosmetic/OpenJavaCV/Learn");
    MatVector images = new MatVector(imageFiles.length);
    Mat labels = new Mat(imageFiles.length, 1, CV_32SC1);
    IntBuffer labelsBuf = labels.createBuffer();
    int counter = 0;
    for (String image : imageFiles) {
        byte[] bytes = recognizeRepo.read(image);
        BytePointer bytePointer = new BytePointer(bytes);
        Mat img = imdecode(new Mat(bytePointer, false), IMREAD_GRAYSCALE);
        images.put(counter, img);
        labelsBuf.put(counter, counter);

        counter++;
    }

    FaceRecognizer faceRecognizer = FisherFaceRecognizer.create();
    // FaceRecognizer faceRecognizer = EigenFaceRecognizer.create();
    // FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();

    faceRecognizer.train(images, labels);

}


    public String recognize(byte[] bytes,double valueConfidence) throws IOException {
//        int predictedLabel = 0;
//        if (bytes != null && bytes.length > 0) {
            BytePointer bytePointer = new BytePointer(bytes);
            Mat testImage = imdecode(new Mat(bytePointer, false), IMREAD_GRAYSCALE);
            IntPointer label = new IntPointer(1);
            DoublePointer confidence = new DoublePointer(1);
            FaceRecognizer faceRecognizer = FisherFaceRecognizer.create();
            faceRecognizer.predict(testImage, label, confidence);
           int  predictedLabel = label.get(0);
            if (confidence.get(0) > valueConfidence) {
                return null;
            }

            return imageFiles[predictedLabel] ;
    }
}
