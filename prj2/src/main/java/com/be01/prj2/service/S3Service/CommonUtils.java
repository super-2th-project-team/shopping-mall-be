package com.be01.prj2.service.S3Service;

import java.util.UUID;

public class CommonUtils {


    public static String buildFileName(String email,String originalFileName ){
        int fileExtensionIndex = originalFileName.lastIndexOf(".");
        String fileExtension = originalFileName.substring(fileExtensionIndex);

        return email + "/" + email;
    }

    public static String buildProductName(Long productId, String originalFileName){
        int fileExtensionIndex = originalFileName.lastIndexOf(".");
        String productIdx = productId.toString();
        String randomString = UUID.randomUUID().toString().substring(0, 8);
        return "Product :"+ productIdx + "/" + productIdx + randomString;
    }
}
