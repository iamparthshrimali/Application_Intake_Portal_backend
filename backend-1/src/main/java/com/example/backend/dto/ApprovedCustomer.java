package com.example.backend.dto;

import org.bson.types.Binary;

import jakarta.persistence.Transient;

public class ApprovedCustomer {
	String customerEmail;
	String reviewerEmail;
	@Transient
	private Binary signedPdf;
	public ApprovedCustomer(String customerEmail, String reviewerEmail, Binary signedPdf) {
		super();
		this.customerEmail = customerEmail;
		this.reviewerEmail = reviewerEmail;
		this.signedPdf = signedPdf;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
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
	@Override
	public String toString() {
		return "ApprovedCustomer [customerEmail=" + customerEmail + ", reviewerEmail=" + reviewerEmail + ", signedPdf="
				+ signedPdf + "]";
	}
	
}
