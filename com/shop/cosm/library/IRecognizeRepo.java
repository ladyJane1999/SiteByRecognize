package com.shop.cosm.library;

import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public interface IRecognizeRepo {

    String add(byte[] bytes,String username,String  extension) throws IOException;

    void delete(String filename);

    String[] readAll(String trainingDir);

    byte[] read(String filename) throws IOException;

}