package com.shop.cosm.library;

import org.springframework.stereotype.Repository;

import java.io.IOException;
@Repository
public interface ISettingRecognize {

    void train() throws IOException;

    String recognize(byte[] bytes,double valueConfidence) throws IOException;
}
