package com.example.backend.service;

import java.io.IOException;

import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {


Binary downloadFile(String bucketName, String filename);


String saveFile(MultipartFile file, String customerEmail, String bucketName) throws IOException;
}
