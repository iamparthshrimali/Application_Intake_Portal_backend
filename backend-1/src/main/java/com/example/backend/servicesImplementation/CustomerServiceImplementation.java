package com.example.backend.servicesImplementation;

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
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//import com.example.backend.EmailService.EmailService;
import com.example.backend.constatns.JWTConstants;
import com.example.backend.dto.JwtPayload;
import com.example.backend.model.Customer;
import com.example.backend.model.CustomerForApprovement;
import com.example.backend.repo.CustomerForApprovementRepo;
import com.example.backend.repo.CustomerRepo;
import com.example.backend.service.CustomerService;
import com.example.backend.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@Service
public class CustomerServiceImplementation implements CustomerService{
	@Autowired
	CustomerRepo custRepo;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	CustomerForApprovement customerForApprovement;

	@Autowired
	CustomerForApprovementRepo customerForApprovementRepo;

	@Override
	public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String username) throws IOException {
		CustomerForApprovement c = customerForApprovementRepo.findByEmail(username).get(0);
	
		Binary binary =c.getPdf();
		InputStream inputStream = new ByteArrayInputStream(binary.getData());

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.pdf");
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

		InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
		return ResponseEntity.ok().headers(headers).body(inputStreamResource);
	}
	
	@Override
	public Map<String, String> validateCustomer(@RequestBody Map<String, Object> requestParams) {
		HashMap<String, String> map = new HashMap<>();
	
		

		String email = (String) requestParams.get("email");
		System.out.println(email);
		System.out.println("method called");
		String fname = (String) requestParams.get("fname");
		String lname = (String) requestParams.get("lname");
		String city = (String) requestParams.get("city");
		String state = (String) requestParams.get("state");
	
		
		if (custRepo.findByEmail(email).size() > 0) {
			Customer customer = custRepo.findByEmail(email).get(0);
			System.out.println("customer found");
			Boolean validFname =false;
			if(fname!=null)
				validFname = customer.getFname().equalsIgnoreCase(fname.trim());
			Boolean validLname  =false;
			if(lname!=null)
				validLname = customer.getLname().equalsIgnoreCase(lname.trim());
			Boolean validCity =false;
			if(city!=null)
				validCity = customer.getCity().equalsIgnoreCase(city.trim());
			Boolean validState =false;
			if(state!=null)
				validState= customer.getState().equals(state.trim());
			map.put("fname", Boolean.toString(validFname));
			map.put("lname", Boolean.toString(validLname));
			map.put("city", Boolean.toString(validCity));
			map.put("state", Boolean.toString(validState));
			map.put("result", "true");
			
		} else
			map.put("result", "Customer not found");
		return map;

	}

	@Override
	public Map<String, String> registerCustomer(@RequestBody Map<String, Object> requestParams,String jwtToken) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
//		 jwtToken = req.getHeader(JWTConstants.JWT_HEADER);
	    JwtPayload payload= JsonUtils.parseJson(jwtToken);
	    
		HashMap<String, String> map = new HashMap<>();
		String approvementStatus="2";

		String email = (String) requestParams.get("email");
		String fname = (String) requestParams.get("fname");
		String lname = (String) requestParams.get("lname");
		String city = (String) requestParams.get("city");
		String address = (String) requestParams.get("address");
		String state = (String) requestParams.get("state");
		String pdf = (String) requestParams.get("pdf");
		pdf = pdf.substring(28);

		byte[] binaryData = Base64.getDecoder().decode(pdf);
		Binary binary = new Binary(binaryData);
		customerForApprovement.setApprovementStatus(approvementStatus);
		customerForApprovement.setFname(fname);
		customerForApprovement.setLname(lname);
		customerForApprovement.setCity(city);
		customerForApprovement.setEmail(email);
		customerForApprovement.setAddress(address);
		customerForApprovement.setState(state);
		customerForApprovement.setApprovementStatus((String)requestParams.get("approvementStatus"));
		String addedBy=(String)requestParams.get("addedBy");
		customerForApprovement.setAddedBy(addedBy);
		customerForApprovement.setPdf(binary);
		 List<CustomerForApprovement> existingRecord = customerForApprovementRepo.findByEmail(email);
		
		 if(existingRecord.size()>0)	 
		 {	
			 
			 
			 CustomerForApprovement record = customerForApprovementRepo.findByEmail(email).get(0);
			 Query query = new Query(Criteria.where("email").is(email));
			 Update update = new Update().set("pdf", binary);
			 mongoTemplate.updateFirst(query, update, "customerForApprovement");
		 }
		 else {
			 customerForApprovementRepo.save(customerForApprovement);
		 }
//		 EmailService.sendmail(customerForApprovement);
		map.put("result", "submitted");
		return map;
	}
	
	@Override
	public List<Customer> getCustomerList() {
		// TODO Auto-generated method stub
		return custRepo.findAll();
	}
	
	@Override
	public List<CustomerForApprovement> getCustomerForApprovement() {
		// TODO Auto-generated method stub
		return customerForApprovementRepo.findAll();
	}
	@Override
	public void addCustomer(Customer customer) {
		custRepo.save(customer);
		
	}
	
	@Override
	public void deleteCustomer(String email) {
		custRepo.deleteByEmail(email);
		
	}
	
//	@Override
//	public void updateCustomer(Customer customer) {
//		// TODO Auto-generated method stub
//		custRepo.(customer);
//		
//		Query q= new Query().addCriteria(Criteria.where("email").is(customer.getEmail()));
//		Update update = mongoTemplate.updateMulti(q, UpdateDefinition, Customer.class); 
//	}

	public List<CustomerForApprovement> getCustomerListForAgent(String email) {
		// TODO Auto-generated method stub		
		return customerForApprovementRepo.findByEmail(email);
	}
	
	public Customer updateCustomer(Customer customer) {
		return custRepo.save(customer);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CustomerForApprovement> getListOfCutomerForReview() {
		// TODO Auto-generated method stub
		return customerForApprovementRepo.findAllWihoutPdf();
	}

	@Override
	public void reviewDoneBy(String customerEmail,String reviewerEmail) {
		// TODO Auto-generated method stub
		
	   customerForApprovementRepo.reviewDoneBy(customerEmail,reviewerEmail);
	}

	@Override
	public void approveCustomer(String customerEmail,String pdf,String jwtToken) throws Exception {
		// TODO Auto-generated method stub
		byte[] binaryData = Base64.getDecoder().decode(pdf);
		Binary binary = new Binary(binaryData);
		JwtPayload payload=JsonUtils.parseJson(jwtToken);
		String reviewedBy=payload.getSub();
		System.out.println(reviewedBy);
	customerForApprovementRepo.approveCustomer(customerEmail,reviewedBy, binary);;
		
		
	}

	@Override
	public Map<String, String> registerCustomer(Map<String, Object> requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reviewDoneBy(String email) {
		// TODO Auto-generated method stub
		
	}


}
