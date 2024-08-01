package com.cashrich.cashrichapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cashrich.cashrichapp.model.JwtRequest;
import com.cashrich.cashrichapp.model.User;
import com.cashrich.cashrichapp.repo.UserRepository;
import com.cashrich.cashrichapp.service.AuthService;

@RestController

public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AuthService authService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepo;

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody JwtRequest jwtRequest) throws Exception {

		try {

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));

			return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication1(jwtRequest));

		} catch (DisabledException e) {
			throw new Exception("USER DISABLE");
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid Creadentials");

		}

	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> createUser(@RequestBody User user) throws Exception {
	    try {
	        if (user == null) {
	            return ResponseEntity.badRequest().build();
	        }

	        String username = user.getUsername();
	        String password = user.getPassword();

	        if (username == null || password == null) {
	            return ResponseEntity.badRequest().build();
	        }

	        // Validate username: alphanumeric, 4-15 characters
	        if (!username.matches("^[a-zA-Z0-9]{4,15}$")) {
	            return ResponseEntity.badRequest().body("Invalid username. Username must be alphanumeric and 4-15 characters long.");
	        }

	        // Validate password: 8-15 characters, at least 1 uppercase, 1 lowercase, 1 digit, and 1 special character
	        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$")) {
	            return ResponseEntity.badRequest().body("Invalid password. Password must be 8-15 characters long and contain at least 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character.");
	        }

	        user.setRole("ROLE_USER");
	        user.setPassword(passwordEncoder.encode(password));

	        User createdUser = userRepo.save(user);

	        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }

	}
}
