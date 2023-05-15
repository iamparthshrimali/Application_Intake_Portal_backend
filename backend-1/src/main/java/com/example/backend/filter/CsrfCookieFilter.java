//package com.example.backend.filter;
//
//import java.io.IOException;
//
//import org.springframework.security.web.csrf.CsrfToken;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class CsrfCookieFilter extends OncePerRequestFilter{
//
//	
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//	        throws ServletException, IOException {
//	    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//	    if (csrfToken != null) {
//	        System.out.println("csrf token ::::  " + csrfToken);
//	        if (csrfToken.getHeaderName() != null) {
//	            System.out.println("csrfToken ::: " + csrfToken.getToken());
//	            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
//	        }
//	    }
//	    filterChain.doFilter(request, response);
//	}
//
//}
//
