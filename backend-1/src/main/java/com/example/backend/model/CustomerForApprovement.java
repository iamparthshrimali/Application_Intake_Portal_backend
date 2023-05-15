package com.example.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.Binary;
import org.springframework.stereotype.Component;

import com.example.backend.dto.ApprovedCustomer;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Document
@Component
public class CustomerForApprovement {
	@Id
	private String email;
	private String fname;
	private String lname;
	private String address;
	private String city;
	private String state;
	private String approvementStatus; // 0->disapproved, 1->Approved, 2->Pending
	private String addedBy;
//	private ApprovedCustomer approvedCustomer;
	private Binary pdf;
	
	
	String reviewerEmail;
	
	private Binary signedPdf;






	public CustomerForApprovement(String email, String fname, String lname, String address, String city, String state,
			String approvementStatus, String addedBy, Binary pdf, String reviewerEmail, Binary signedPdf) {
		super();
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.address = address;
		this.city = city;
		this.state = state;
		this.approvementStatus = approvementStatus;
		this.addedBy = addedBy;
		this.pdf = pdf;
		this.reviewerEmail = reviewerEmail;
		this.signedPdf = signedPdf;
	}

	public CustomerForApprovement() {
		super();
	}

	public String getApprovementStatus() {
		return approvementStatus;
	}

	public void setApprovementStatus(String approvementStatus) {
		this.approvementStatus = approvementStatus;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Binary getPdf() {
		return pdf;
	}

	public void setPdf(Binary pdf) {
		this.pdf = pdf;
	}

	@Override
	public String toString() {
		return "CustomerForApprovement [email=" + email + ", fname=" + fname + ", lname=" + lname + ", address="
				+ address + ", city=" + city + ", state=" + state + ", approvementStatus=" + approvementStatus
				+ ", addedBy=" + addedBy + ", pdf=" + pdf + ", reviewerEmail=" + reviewerEmail + ", signedPdf="
				+ signedPdf + "]";
	}

	public String getReviewerEmail() {
		return reviewerEmail;
	}

	public void setReviewerEmail(String reviewerEmail) {
		this.reviewerEmail = reviewerEmail;
	}

	public Binary getSignedPdf() {
		return signedPdf;
	}

	public void setSignedPdf(Binary signedPdf) {
		this.signedPdf = signedPdf;
	}






}
