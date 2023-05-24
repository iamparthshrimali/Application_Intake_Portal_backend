package com.example.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.constatns.JWTConstants;
import com.example.backend.dto.CustomerForApprovementDto;
import com.example.backend.model.CustomerForApprovement;
import com.example.backend.repo.CustomerForApprovementRepo;
import com.example.backend.servicesImplementation.CustomerServiceImplementation;
import com.example.backend.servicesImplementation.S3ServiceImplementation;

import jakarta.servlet.http.HttpServletRequest;
@RestController
public class AgentController {

	
	  @Autowired
	    private S3ServiceImplementation s3Service;;
		
		@Autowired
		CustomerServiceImplementation customerService;

		@Autowired
		CustomerForApprovementRepo cr;
	
	@GetMapping("/getCustomersListForAgent/{email}")
	public List<CustomerForApprovement> getAllCustomersForAgent(@PathVariable String email) {
		System.out.println(email);
		
//		System.out.println(customerService.getCustomerListForAgent(email));
		return customerService.getCustomerListForAgent(email);
	}
	
	@RequestMapping("/registerCustomer")
	public Map<String, String> registerCustomer(@ModelAttribute CustomerForApprovementDto customerForApprovementDto,HttpServletRequest req) throws Exception {
		
		String jwtToken = req.getHeader(JWTConstants.JWT_HEADER);   
		return customerService.registerCustomer(customerForApprovementDto,jwtToken);
	}
}
