package com.example.backend.servicesImplementation;

import java.io.File;
import java.io.FileOutputStream;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.backend.service.S3Service;

import java.io.IOException;

@Service
public class S3ServiceImplementation implements S3Service {

	
	private  final AmazonS3 s3;
	

	 public S3ServiceImplementation(AmazonS3 s3) {
	        this.s3 = s3;
	    }

	@Override
	public String saveFile(MultipartFile file,String customerEmail) throws IOException {
		  String filename = customerEmail;
	       
	        while(true) {
	            try {
	                File file1 = convertMultiPartToFile(file);
	                PutObjectResult putObjectResult = s3.putObject("customerforapprovement", filename, file1);
	                System.out.println(putObjectResult.getContentMd5()); 
	                return "file uploaded succesfully";
	            } catch (IOException e) {
	                System.out.println("Error occured while uploading file "+e);
	            }
	        }
		
	}

	@Override
	public Binary downloadFile(String filename) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private File convertMultiPartToFile(MultipartFile file) throws IOException, java.io.IOException {

	    File convFile = new File(file.getOriginalFilename());
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(file.getBytes());
	    fos.close();
	    return convFile;
	}


}
