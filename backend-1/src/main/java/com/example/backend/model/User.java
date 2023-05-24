package com.example.backend.model;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Transient;

@Document
@Component
public class User {

	@Id
	@Indexed(unique = true)
	private String id;
	private String email;
	private String role;
	private String mono;
	private String password;
	private int is_approved;
	private int isVerified;

	public int getIs_approved() {
		return is_approved;
	}

	public void setIs_approved(int is_approved) {
		this.is_approved = is_approved;
	}

	public User() {
		super();
	}

	

	public User(String id, String email, String role, String mono, String password, int is_approved, int isVerified) {
		super();
		this.id = id;
		this.email = email;
		this.role = role;
		this.mono = mono;
		this.password = password;
		this.is_approved = is_approved;
		this.isVerified = isVerified;
	}

	public int getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(int isVerified) {
		this.isVerified = isVerified;
	}

	public User(String email, String role, String mono, String password) {
		super();
		this.email = email;
		this.role = role;
		this.mono = mono;
		this.password = password;
	}

	public User(String id, String email, String role, String mono, String password,int is_approved) {
		super();
		this.id = id;
		this.email = email;
		this.role = role;
		this.mono = mono;
		this.password = password;
		this.is_approved=is_approved;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMono() {
		return mono;
	}

	public void setMono(String mono) {
		this.mono = mono;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", role=" + role + ", mono=" + mono + ", password=" + password
				+ ", is_approved=" + is_approved + ", is_verified=" + is_verified + "]";
	}



}
