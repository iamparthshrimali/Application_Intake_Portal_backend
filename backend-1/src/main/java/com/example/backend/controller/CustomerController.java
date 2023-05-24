package com.example.backend.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.aggregation.StringOperators.Substr;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.constatns.JWTConstants;
import com.example.backend.dto.CustomerForApprovementDto;
import com.example.backend.dto.JwtPayload;
import com.example.backend.model.Customer;
import com.example.backend.model.CustomerForApprovement;
import com.example.backend.repo.CustomerForApprovementRepo;

import com.example.backend.servicesImplementation.CustomerServiceImplementation;
import com.example.backend.servicesImplementation.S3ServiceImplementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class CustomerController {

    @Autowired
    private S3ServiceImplementation s3Service;;
	
	@Autowired
	CustomerServiceImplementation customerService;

	@Autowired
	CustomerForApprovementRepo cr;





	@RequestMapping("/getCustomersList")
	public List<Customer> getAllCustomers() {
		return customerService.getCustomerList(); 
	}
	
	// new things
	@PostMapping("/addCustomer")
	public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
		customerService.addCustomer(customer);
		
		return new ResponseEntity<String>("Customer added succesfully", HttpStatus.OK);
	}
	
	@GetMapping("/deleteCustomer")
	public ResponseEntity<String> deleteCustomer(@RequestParam String email) {
		customerService.deleteCustomer(email);
		
		return new ResponseEntity<String>("Customer deleted succesfully", HttpStatus.OK);
	}
	
	@PostMapping("/updateCustomer")
	public ResponseEntity<String> updateCustomer(@RequestBody Customer customer) {
		customerService.updateCustomer(customer);
		
		return new ResponseEntity<String>("Customer updated succesfully", HttpStatus.OK);
	}
	


	

}
