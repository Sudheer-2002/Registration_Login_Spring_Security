package com.spring_security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
//	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
//				.csrf().disable() 
				.csrf(customizer -> customizer.disable())    //1
				.authorizeHttpRequests(requests -> requests
													.requestMatchers("/students/register","/students/login").permitAll()
													.anyRequest().authenticated())   //2
				.formLogin(Customizer.withDefaults())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
//	@SuppressWarnings("deprecation")
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user1=User
//							.withDefaultPasswordEncoder()
//							.username("sudheer")
//							.password("s@123")
//							.roles("ADMIN")
//							.build();
//		UserDetails user2=User
//							.withDefaultPasswordEncoder()
//							.username("masi")
//							.password("m@123")
//							.roles("USER")
//							.build();
//		UserDetails user3=User
//							.withDefaultPasswordEncoder()
//							.username("sriluuu")
//							.password("p@123")
//							.roles("USER")
//							.build();
//		return new InMemoryUserDetailsManager(user1,user2,user3);
//	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
		
	}

}
