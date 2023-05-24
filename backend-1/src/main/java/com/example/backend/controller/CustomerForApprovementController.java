package com.example.backend.controller;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.constatns.JWTConstants;
import com.example.backend.dto.CustomerForApprovementDto;
import com.example.backend.repo.CustomerForApprovementRepo;
import com.example.backend.servicesImplementation.CustomerServiceImplementation;
import com.example.backend.servicesImplementation.S3ServiceImplementation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class CustomerForApprovementController {

    @Autowired
    private S3ServiceImplementation s3Service;;
	
	@Autowired
	CustomerServiceImplementation customerService;

	@Autowired
	CustomerForApprovementRepo cr;

	
	@PostMapping("/approveCustomer")
	public  ResponseEntity<String> approve(@ModelAttribute CustomerForApprovementDto customerForApprovementDto,HttpServletRequest req) throws Exception {
		MultipartFile pdf= (MultipartFile) customerForApprovementDto.getSignedPdf();
		String customerEMail=(String) customerForApprovementDto.getEmail();
//		System.out.println("customer s email is ",customerEMail);
		System.out.println("customers email is "+customerEMail);
		String jwtToken = req.getHeader(JWTConstants.JWT_HEADER);   
	
		 customerService.approveCustomer(customerEMail,pdf,jwtToken);
//		customerService.approveCustomer(customerForApprovement);
		return new ResponseEntity<>("Customer appproved succesfully",HttpStatus.OK);
	}
	
	@RequestMapping("/validateCustomer")
	public Map<String, String> validateCustomer(@RequestBody Map<String, Object> requestParams,HttpServletRequest req) throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
//		System.out.println("hello validate code......");
	
		return customerService.validateCustomer(requestParams);

	}

	
	
}
