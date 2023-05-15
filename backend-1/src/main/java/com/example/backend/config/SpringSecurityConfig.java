package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.oauth2.login.UserInfoEndpointDsl;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.time.Duration;
import java.util.Collections;

//import com.example.backend.filter.CsrfCookieFilter;
import com.example.backend.filter.JWTTTokenValidation;
import com.example.backend.service.UserInfoUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	// User for creating Autowire of authentication management
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

		System.out.println();

		return config.getAuthenticationManager();
	}

	@Bean
	// authentication
	public UserDetailsService userDetailsService() {

		return new UserInfoUserDetailsService();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// csrf
		CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
		CookieCsrfTokenRepository cookieCsrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
		cookieCsrfTokenRepository.setCookieDomain("localhost");
		cookieCsrfTokenRepository.setParameterName("_csrf");
		cookieCsrfTokenRepository.setHeaderName("X-XSRF-TOKEN");
		cookieCsrfTokenRepository.setCookieName("XSRF-TOKEN");
//		cookieCsrfTokenRepository.setCookieMaxAge((int) Duration.ofMinutes(15).getSeconds());

		http.cors().configurationSource(new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

				CorsConfiguration config = new CorsConfiguration();

				config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setMaxAge(3600L);
				return config;
			}
		}).and().csrf(csrf -> csrf.csrfTokenRequestHandler(requestHandler)
				.csrfTokenRepository(cookieCsrfTokenRepository)
				.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()))

//				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new JWTTTokenValidation(), BasicAuthenticationFilter.class).authorizeHttpRequests()
				.requestMatchers("/approveCustomer", "/getListOfCutomerForReview").hasAnyRole("REVIEWER", "ADMIN")
				.requestMatchers("/getCustomersList", "/addCustomer", "/deleteCustomer", "/updateCustomer",
						"/getAllEmployees", "/isApprove")
				.hasRole("ADMIN").requestMatchers("/getCustomersListForAgent/*").hasRole("AGENT")

				.requestMatchers("/user", "/register").permitAll().anyRequest().authenticated()

		;

		return http.build();
	}

	// password encoder
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}
