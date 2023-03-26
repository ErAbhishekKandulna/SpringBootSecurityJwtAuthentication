package com.app.raghu.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.raghu.entity.User;
import com.app.raghu.payload.UserRequest;
import com.app.raghu.payload.UserResponse;
import com.app.raghu.service.IUserService;
import com.app.raghu.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserRestController 
{
	@Autowired
	private IUserService service;
	
	@Autowired
	private JwtUtil util;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user)
	{
		service.saveUser(user);
		return ResponseEntity.ok("USER CREATED");
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest)
	{
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						userRequest.getUsername(), 
						userRequest.getPassword())
				);
		String token = util.generateToken(userRequest.getUsername());
		
		return ResponseEntity.ok(new UserResponse(token, "Generated By Mr.Raghu"));
	}
	
	@PostMapping("/welcome")
	public ResponseEntity<String> showUserData(Principal p)
	{
		return ResponseEntity.ok("Hello :" + p.getName());
	}
}
