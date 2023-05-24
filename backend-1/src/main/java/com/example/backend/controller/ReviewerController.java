package com.example.backend.controller;

import java.io.IOException;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.CustomerForApprovement;
import com.example.backend.repo.CustomerForApprovementRepo;
import com.example.backend.servicesImplementation.CustomerServiceImplementation;
import com.example.backend.servicesImplementation.S3ServiceImplementation;


@RestController
public class ReviewerController {



    @Autowired
    private S3ServiceImplementation s3Service;;
	
	@Autowired
	CustomerServiceImplementation customerService;

	@Autowired
	CustomerForApprovementRepo cr;


	@RequestMapping("/retrieveFile2")
	public Binary downloadFile2(@RequestParam String email) throws IOException {
		
		return customerService.downloadFile("customerforapprovement",email);
	}

	@RequestMapping("/retrieveFileForApprovedCustomer")
	public Binary downloadFileForApprovedCustomer(@RequestParam String email) throws IOException {
		
		return customerService.downloadFile("approvedcustomers",email);
	}

	
	

	
	
	@GetMapping("/getListOfCutomerForReview")
	public List<CustomerForApprovement> getListOfCutomerForReview() {
//		System.out.println(customerService.getCustomerListForAgent(email));
		return customerService.getListOfCutomerForReview();
	}
}
