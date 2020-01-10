package com.springboot.api.appserve.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.api.appserve.jwt.JwtTokenProvider;
import com.springboot.api.appserve.models.Role;
import com.springboot.api.appserve.models.User;
import com.springboot.api.appserve.services.IUserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private IUserService iUserService;
	
	@PostMapping("/registration")
	public ResponseEntity<?> register(@RequestBody User user) {
		if (iUserService.findByUsername(user.getUsername()) != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		user.setRole(Role.USER);
		return new ResponseEntity<>(iUserService.saveUser(user), HttpStatus.CREATED);
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> login(Principal principal) {
		if (principal == null) {
			return ResponseEntity.ok(principal);
		}
		
		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
		User user = iUserService.findByUsername(authenticationToken.getName());
		user.setToken(jwtTokenProvider.generateToken(authenticationToken));
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
