package com.shop.cosm.recognize;

import com.shop.cosm.library.IRecognizeRepo;

import java.io.*;
import java.util.UUID;

public class RecognizeRepo implements IRecognizeRepo {

    @Override
    public String add(byte[] bytes, String username,String  extension) throws IOException {
        UUID uuid = UUID.randomUUID();
        String filename = username +uuid +"." + extension;
        File file = new File(filename);
        try (FileOutputStream stream = new FileOutputStream("D:/SiteRepo/" + file)) {
            stream.write(bytes);
        }
        return filename;
    }
    @Override
    public String[] readAll( String trainingDir) {
//        String trainingDir = "D:/Person/";
        File root = new File(trainingDir);

        FilenameFilter imgFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                name = name.toLowerCase();
                return name.endsWith(".jpg") || name.endsWith(".pgm") || name.endsWith(".png");
            }
        };

        File[] imageFiles = root.listFiles(imgFilter);

        String[] imgh = new String[imageFiles.length];
        int j = 0;
        for (File file : imageFiles) {

            imgh[j] = file.getAbsolutePath();
            j++;
        }

        return imgh;
    }


    @Override
    public byte[] read(String filename) throws IOException {
        File image = new File(filename);


        FileInputStream img = new FileInputStream(filename);
        long n = image.length();
        byte[] b = new byte[(int)n];
        img.read(b);
        return  b;
    }

    @Override
    public void delete(String fileid) {
        String uploadPath = "D:/Person/";
        File file1 = new File(uploadPath + "/"+ fileid);
        file1.delete();

    }

}

