package com.example.backend.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import com.example.backend.model.User;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
	
	List<User> findByEmailAndPassword(String email,String password);
	Optional<User> findByEmail(String email);
	void deleteByEmail(String email);

	
	
	@Query("{ 'email' : ?0  }")
	@Update("{ '$set' : { 'is_approved' :?1} }")
    void updateIsApproved(String email, int b);
	
	 @Query("{ 'isVerified' : 1 }")
	 List<User> findAllVerifiedUsers();
	 
	 @Query("{ 'email' : ?0  }")
	@Update("{ '$set' : { 'password' :?1,isVerified:1 }}")
	void setEmployeePassWord(String email, String hashPassword);

}
