package com.example.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserAccountVerification {
	private String verification_id;
	private String email;
	private String mobile_number;
	private String role;
	private String password;
	
	public UserAccountVerification(String verification_id, String email, String mobile_number, String role,
			String password) {
		super();
		this.verification_id = verification_id;
		this.email = email;
		this.mobile_number = mobile_number;
		this.role = role;
		this.password = password;
	}

	public String getVerification_id() {
		return verification_id;
	}

	public void setVerification_id(String verification_id) {
		this.verification_id = verification_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserAccountVerification [verification_id=" + verification_id + ", email=" + email + ", mobile_number="
				+ mobile_number + ", role=" + role + ", password=" + password + "]";
	}
	
	
}
