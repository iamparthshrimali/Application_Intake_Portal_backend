package com.example.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.User;
import com.example.backend.repo.UserRepo;
import com.example.backend.service.UserService;
import com.example.backend.servicesImplementation.EmailServiceImplementation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class EmployeeController {

	@Autowired
	private EmailServiceImplementation emailServiceImplementation;

	@Autowired
	UserRepo ur;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;

	@PostMapping("/register")
	public void register(@RequestBody User user, HttpServletRequest request) {

//		String hashPassword = passwordEncoder.encode(user.getPassword());
//		user.setPassword(hashPassword);
		user.setIsVerified(0);
		HashMap<String, String> map = new HashMap<>();
		user.setIs_approved(0);
		String verification_id = UUID.randomUUID().toString().replace("-", "");
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(900);
		long expirationTimeMillis = System.currentTimeMillis() + (15*60*1000); // 15 minutes from now
		String expirationTimeParam = expirationTimeMillis + "";

		String link = "http://localhost:3000/password/" + user.getEmail() + "/" + verification_id + "/"
				+ expirationTimeParam;

		emailServiceImplementation.sendEmail(user.getEmail(), "Application intake portal",
				"Registration successful! Click the link below to set your password:<br><a href='" + link
						+ "'>Set Password</a>");

		map.put(user.getEmail(), verification_id);
		session.setAttribute(user.getEmail(), map);
		session.setMaxInactiveInterval(900);
		System.out.println(session.getAttribute(user.getEmail()));
		ur.save(user);

	}

	@PostMapping("/verify")
	public ResponseEntity<String> verify(@RequestBody Map<String,String> requestParams,HttpServletRequest request) {

		
//		String hashPassword = passwordEncoder.encode(user.getPassword());
		String email=requestParams.get("email");
//		user.setPassword(hashPassword);
		Optional<User>savedUser = ur.findByEmail(email);
	    if(savedUser.isPresent() )
	    {
	    	
//	    	if(savedUser.get().getPassword()!=null) {
	    		HttpSession session=request.getSession();
	    		System.out.println("token recieved from front end "+requestParams.get("verification_id"));
	    	
	    		HashMap<String, String> map=(HashMap)session.getAttribute(email);
	    		System.out.println(map);
	    		if(map==null) {
	    			System.out.println("verification failed");
	    			return new ResponseEntity<String>("Verification Failed", HttpStatus.UNAUTHORIZED);
	    		}
				if(map.get(email).equals(requestParams.get("verification_id")))
				{
					String hashPassword = passwordEncoder.encode(requestParams.get("password"));
					ur.setEmployeePassWord(email,hashPassword);
					return new ResponseEntity<String>("Verification Successfull", HttpStatus.OK);
				}
				else 
					return new ResponseEntity<String>("Verification Failed", HttpStatus.UNAUTHORIZED);
			    
//	    	}
	    	
	    }
	  
        return new ResponseEntity<String>("Customer Not  Found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getAllEmployees")
	public List<User> getEmployeeList() {
		System.out.println("getting employees");
		return userService.getAllUsers();
	}

	@PostMapping("/delete")
	public String deleteByUsername(@RequestBody User user) {
		System.out.println(user);
		return userService.deleteByEmail(user.getEmail());
	}

	@PostMapping("/isApprove")
	public String isApprove(@RequestBody User user) {
		userService.updateByEmail(user.getEmail());
		emailServiceImplementation.sendEmail(user.getEmail(), "Application Intake Portal",
				"Congratulations,You are successfully approved by admin Now you can login with following Link <br> <strong><a href='http://localhost:3000/"
						+ user.getEmail() + "'>Login</a>'</strong>");
		return "status updated";
	}
}
