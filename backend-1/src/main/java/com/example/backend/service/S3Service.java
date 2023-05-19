package com.example.backend.service;

import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
	String saveFile(MultipartFile file,String customerEmail) throws java.io.IOException;

   Binary downloadFile(String filename);
}
