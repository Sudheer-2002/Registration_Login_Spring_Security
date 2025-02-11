package com.spring_security.controller;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_security.model.Student;
import com.spring_security.model.Users;
import com.spring_security.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/hii")
	public String hi() {
		return "hiii";
	}
	
	@GetMapping("/session-id")
	public String m(HttpServletRequest request) {
		return "Welcome "+request.getSession().getId();
	}
	
	@GetMapping("/csrf-token")
	public CsrfToken getCsrfToken(HttpServletRequest request) {
		return (CsrfToken) request.getAttribute("_csrf");
	}
	
//	List<Student> students=new ArrayList<>(List.of(
//			new Student(1,"sudheer","m"),
//			new Student(2,"masi","f")
//			));
	
//	@GetMapping("/getAll")
//	public List<Student> getStudents(){
//		return students;
//	}
	
	@PostMapping("/register")
	public Users insert(@RequestBody Users user) {
		try {
			System.out.println("Request received at /register endpoint");
			System.out.println("Recieved user "+user);
			return service.register(user);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("User registration failed", e);
		}
	}
	
	@PostMapping("/login")
	public String login(@RequestBody Users user) {
//		System.out.println(user);
//		return "success";
		System.out.println(service.verify(user));
		return service.verify(user);
	}
	
	@GetMapping("/getAll")
	public List<Users> getAll(){
		return service.allStudnets();
	}
}
