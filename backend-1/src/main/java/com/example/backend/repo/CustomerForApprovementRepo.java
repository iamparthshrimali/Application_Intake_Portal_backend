package com.example.backend.repo;

import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.example.backend.model.Customer;
import com.example.backend.model.CustomerForApprovement;

public interface CustomerForApprovementRepo extends MongoRepository<CustomerForApprovement, String> {
	 
	

	 List<CustomerForApprovement> findByEmail(String email);
	 
	 @Query(value = "{'approvementStatus' :'2'}", fields = "{ 'pdf' : 0 }")
	 List<CustomerForApprovement> findAllWihoutPdf();  
  
	 @Query(value="{ 'addedBy' : ?0 }", fields="{ 'pdf' : 0}")
	List<CustomerForApprovement> findByAddedBy(String email);

	
		
	@Query("{ 'email' : ?0  }")
	@Update("{ '$set' : { 'approvementStatus' :?1} }")
	void reviewDoneBy(String customerEmail,String reveiwerEmail);
	
	//approving customer
	@Query("{ 'email' : ?0  }")
	@Update("{ '$set' : { 'approvementStatus' :1,'signedPdf':?2,'reviewerEmail':?1} }")
	void approveCustomer(String customerEmail,String reviewBy,Binary pdf);
	
	

	
	
}
 

