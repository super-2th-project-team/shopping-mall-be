package com.be01.prj2.service.S3Service;

import java.util.UUID;

public class CommonUtils {


    public static String buildFileName(String email,String originalFileName ){
        int fileExtensionIndex = originalFileName.lastIndexOf(".");
        String fileExtension = originalFileName.substring(fileExtensionIndex);

        return email + "/" + email;
    }

    public static String buildProductName(String productName, String originalFileName){

        String randomString = UUID.randomUUID().toString().substring(0, 8);
        return "Product :"+ productName + "/" + productName + randomString;
    }
}
