package com.shop.cosm.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ResponseModel {
    private String username;
    private String password;
    private MultipartFile file;

}
