package com.project.fileservice.Model;

import lombok.Data;

import java.io.ByteArrayInputStream;

@Data
public class FileDTO{
    private String contentType;
    private Long contentLength;
    private byte[] inputStream;
    private String fileName;


}
