package com.be01.prj2.service.S3Service;

public class CommonUtils {


    public static String buildFileName(String email,String originalFileName ){
        int fileExtensionIndex = originalFileName.lastIndexOf(".");
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return email + "/" + fileName + "/" + now + fileExtension;
    }
}
