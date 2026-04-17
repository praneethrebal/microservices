package com.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;



@SuppressWarnings("deprecation")
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final UserDetailsService userDetailsService;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
		
		
		http.authorizeHttpRequests(auth->auth.requestMatchers("/auth/**","/error/**").permitAll().anyRequest().authenticated());
		http.sessionManagement(ses->ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(csrf->csrf.disable());
		http.formLogin(f->f.disable());
		http.httpBasic(Customizer.withDefaults());
		http.cors(Customizer.withDefaults());
		
		
		return http.build();
		
	}
	
	
	@Bean
	public AuthenticationProvider authProvider() throws Exception {
		DaoAuthenticationProvider dao=new DaoAuthenticationProvider(userDetailsService);
		dao.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return dao;
	}
	
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config)
	{

		return config.getAuthenticationManager();
	}
	
	
	

}
