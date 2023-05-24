package com.example.backend.service;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.CustomerForApprovementDto;
import com.example.backend.model.Customer;
import com.example.backend.model.CustomerForApprovement;
import com.example.backend.repo.CustomerForApprovementRepo;
import com.example.backend.repo.CustomerRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface CustomerService {

	public Map<String, String> validateCustomer(@RequestBody Map<String, Object> requestParams);
	public Map<String, String> registerCustomer(CustomerForApprovementDto customerDto,String jwtToken) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException,Exception;
	public List<Customer> getCustomerList();
	public  void addCustomer(Customer customer);
	void deleteCustomer(String email);
	public Customer updateCustomer(Customer customer);
	public List<CustomerForApprovement> getCustomerForApprovement();
	List<CustomerForApprovement> getListOfCutomerForReview();
	void reviewDoneBy(String email);

	void reviewDoneBy(String customerEmail, String reviewerEmail);
	Map<String, String> registerCustomer(CustomerForApprovement customer, String jwtToken) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException;
	
	Binary downloadFile(String username, String bucketName) throws IOException;
	void approveCustomer(String customerEmail, MultipartFile pdf, String jwtToken) throws Exception;
	
//	void updateCustomer(Customer customer);
}