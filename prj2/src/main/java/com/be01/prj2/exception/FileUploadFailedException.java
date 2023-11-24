package com.be01.prj2.exception;

import org.apache.tomcat.util.http.fileupload.FileUploadException;

public class FileUploadFailedException extends FileUploadException {

    public FileUploadFailedException(String msg) {
        super(msg);
    }
}