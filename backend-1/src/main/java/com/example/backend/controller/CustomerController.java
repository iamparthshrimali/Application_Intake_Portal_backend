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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.constatns.JWTConstants;
import com.example.backend.dto.JwtPayload;
import com.example.backend.model.Customer;
import com.example.backend.model.CustomerForApprovement;
import com.example.backend.repo.CustomerForApprovementRepo;
import com.example.backend.repo.CustomerRepo;
import com.example.backend.service.CustomerService;
import com.example.backend.servicesImplementation.CustomerServiceImplementation;
import com.example.backend.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class CustomerController {

	@Autowired
	CustomerServiceImplementation customerService;

	@Autowired
	CustomerForApprovementRepo cr;

	@RequestMapping("/retrieveFile")
	public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String username) throws IOException {
		return customerService.downloadFile(username);
	}

	@RequestMapping("/retrieveFile2")
	public Binary downloadFile2(@RequestParam String email) throws IOException {
		System.out.println("retriving");
		Binary data = cr.findByEmail(email).get(0).getPdf();
		String pdf = data.toString().substring(16);
		System.out.println(pdf);
		return cr.findByEmail(email).get(0).getPdf();
	}

	@RequestMapping("/validateCustomer")
	public Map<String, String> validateCustomer(@RequestBody Map<String, Object> requestParams,HttpServletRequest req) throws UnsupportedEncodingException, JsonMappingException, JsonProcessingException {
//		System.out.println("hello validate code......");
	
		return customerService.validateCustomer(requestParams);

	}

	@RequestMapping("/registerCustomer")
	public Map<String, String> registerCustomer(@RequestBody Map<String, Object> requestParams,HttpServletRequest req) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		
		String jwtToken = req.getHeader(JWTConstants.JWT_HEADER);   
		return customerService.registerCustomer(requestParams,jwtToken);
	}

	@PostMapping("/approveCustomer")
	public  ResponseEntity<String> approve(@RequestBody Map<String, Object> requestParams,HttpServletRequest req) throws Exception {
		String pdf=(String) requestParams.get("signedPdf");
		String customerEMail=(String) requestParams.get("customerEmail");
		
		
		String jwtToken = req.getHeader(JWTConstants.JWT_HEADER);   
	
		 customerService.approveCustomer(customerEMail,pdf,jwtToken);
//		customerService.approveCustomer(customerForApprovement);
		return new ResponseEntity<>("Customer appproved succesfully",HttpStatus.OK);
	}


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

	@GetMapping("/getCustomersListForAgent/{email}")
	public List<CustomerForApprovement> getAllCustomersForAgent(@PathVariable String email) {
		System.out.println(email);
		System.out.println(customerService.getCustomerListForAgent(email));
//		System.out.println(customerService.getCustomerListForAgent(email));
		return customerService.getCustomerListForAgent(email);
	}
	
	@GetMapping("/getListOfCutomerForReview")
	public List<CustomerForApprovement> getListOfCutomerForReview() {
//		System.out.println(customerService.getCustomerListForAgent(email));
		return customerService.getListOfCutomerForReview();
	}
	
//	@GetMapping("/reviewDoneBy/{email}")
//	public List<CustomerForApprovement> reviewDoneBy(@PathVariable String email ) {
////		System.out.println(customerService.getCustomerListForAgent(email));
//		return customerService.reviewDoneBy(email);
//	}

}
