package com.example.backend.servicesImplementation;

import java.io.File;
import java.io.FileOutputStream;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.backend.service.S3Service;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class S3ServiceImplementation implements S3Service {

	private final AmazonS3 s3;

	public S3ServiceImplementation(AmazonS3 s3) {
		this.s3 = s3;
	}

	@Override
	public String saveFile(MultipartFile file, String customerEmail,String bucketName) throws IOException {
		String filename = customerEmail;

		while (true) {
			try {
				File file1 = convertMultiPartToFile(file);
				PutObjectResult putObjectResult = s3.putObject(bucketName, filename, file1);
				System.out.println(putObjectResult.getContentMd5());
				return "file uploaded succesfully";
			} catch (IOException e) {
				System.out.println("Error occured while uploading file " + e);
			}
		}

	}

	@Override
	public Binary downloadFile(String bucketName,String filename) {
		S3Object object = s3.getObject(bucketName, filename);
		// TODO Auto-generated method stub	S3Object object = s3.getObject("customerforapprovement", username);
		S3ObjectInputStream objectContent = object.getObjectContent();
		try {
			return new Binary(objectContent.readAllBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException, java.io.IOException {

		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private File convertBinaryFileToFile(Binary file) {

		try {
			File tempFile = Files.createTempFile(null, null).toFile();

			FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
			fileOutputStream.write(file.getData());
			fileOutputStream.close();

			// Use the tempFile as needed (e.g., upload to S3 bucket)

			// Once done, delete the temp file
			tempFile.delete();
			return tempFile;
			
		} catch (IOException e) {
			// Handle any exceptions
			e.printStackTrace();
		}
		return null;
	}

}
