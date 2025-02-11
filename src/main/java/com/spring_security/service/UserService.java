package com.spring_security.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring_security.model.Users;
import com.spring_security.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Transactional
	public Users register(Users user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}
	
//	public Users register(Users user) {
//	    String encodedPassword = encoder.encode(user.getPassword());
//	    System.out.println("Encoded password: " + encodedPassword); // Debugging line
//	    user.setPassword(encodedPassword);
//	    Users savedUser = repo.save(user);
//	    System.out.println("User saved: " + savedUser); // Debugging line
//	    return savedUser;
//	}
	
	public List<Users> allStudnets(){
		return repo.findAll();
	}

	public String verify(Users user) {
		Authentication authentication=
				authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getUsername());
		}
		return "fail";
	}
}

