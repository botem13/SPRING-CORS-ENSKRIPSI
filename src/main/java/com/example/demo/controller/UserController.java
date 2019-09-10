package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;



@CrossOrigin(origins="")
@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/users")
	public List<User>getAllUsers(){
		System.out.println("Get all Users.....");
		
		List<User>users=new ArrayList<>();
		repository.findAll().forEach(users::add);
		return users;
	}
	
	@PostMapping("/user")
	public User createUser(@Valid @RequestBody User user) {

		// test enskripsi password
		
		String password=user.getPassword(); 		
		String encryptPwd = passwordEncoder.encode(password);
		user.setPassword(encryptPwd);
		// SET ROLE 
		user.setRole("customer");
		return repository.save(user);
	}
	
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {
		System.out.println("Delete Customer with ID = " + id + "...");

		repository.deleteById(id);

		return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
	}
	
	
	@GetMapping("user/role/{role}")
	public List<User> findByAge(@PathVariable String role) {

		List<User> user = repository.findByRole(role);
		return user;
	}
	
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		System.out.println("Update Customer with ID = " + id + "...");

		Optional<User> userData = repository.findById(id);

		if (userData.isPresent()) {
			User _user = userData.get();
			_user.setFirst_name(user.getFirst_name());
			_user.setLast_name(user.getLast_name());
			_user.setEmail(user.getEmail());
			_user.setPassword(user.getPassword());
			_user.setRole(user.getRole());
			return new ResponseEntity<>(repository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	
	}
	
	}
	}
	
	
	
	
	


