package com.example.backend.servicesImplementation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.backend.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailServiceImplementation implements EmailService {

	 @Autowired
	 private  JavaMailSender mailSender;
	 
	 ExecutorService executor = Executors.newFixedThreadPool(1);
	 
	@Override
	public  void sendEmail(String to, String subject, String body) {
		// TODO Auto-generated method stub
		 CompletableFuture.runAsync(() -> {
		        // Send email asynchronously
			 	MimeMessage message = mailSender.createMimeMessage();
			    MimeMessageHelper helper;
				try {
					helper = new MimeMessageHelper(message, true, "UTF-8");
					 	helper.setTo(to);
				        helper.setSubject(subject);
				        helper.setText(body, true); 
				        mailSender.send(message);
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
//					 int maxRetries = 3;
//			            int retryCount = 0;
//			            
//			            while (retryCount < maxRetries) {
//			                try {
//			                    // Wait for a short duration before retrying
//			                    Thread.sleep(3000);
//			                    
//			                    // Retry sending the email
//			                    sendEmail(to, subject, body);
//			                    break; // Exit the loop if the email is sent successfully
//			                } catch (Exception e) {
//			                    retryCount++;
//			                    if (retryCount >= maxRetries) {
//			                        e1.printStackTrace();
//			                    }
//			                }
//			            }
				}
		    }, executor);
	

		    
	}
  
}
