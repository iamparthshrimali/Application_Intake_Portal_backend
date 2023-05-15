//package com.example.backend.EmailService;
//
//import java.util.Properties;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.MailParseException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import com.example.backend.model.CustomerForApprovement;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//
//@Configuration
//class EmailConfig {
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("your.mail.host.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("your.username");
//        mailSender.setPassword("your.password");
// 
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
// 
//        return mailSender;
//    }
//}
//
//
//@Service
//public class EmailService {
//	
//	@Autowired
//	private static JavaMailSender javaMailSender;
//
//		public static void sendHtmlEmail(String to, String subject, String body) {
//	        MimeMessage message = javaMailSender.createMimeMessage();
//	 
//	        try {
//	            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//	            helper.setFrom("shrimaliparth1@gmail.com");
//	            helper.setTo(to);
//	            helper.setSubject(subject);
//	            helper.setText(body);
//	 
//	            try {
//					javaMailSender.send(message);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	        } catch (MessagingException e) {
//	            throw new MailParseException(e);
//	        }
//	    }
//		
//		public static void sendmail(CustomerForApprovement customerForApprovement) {
//			
//			sendHtmlEmail("dwarkeshdhamecha2582@gmail.com", "Hello Dwarkesh", "Hello from parth...");
//	}
//
//}
