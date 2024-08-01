package com.cashrich.cashrichapp.service;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.cashrich.cashrichapp.config.JwtTokenUtil;
import com.cashrich.cashrichapp.model.JwtRequest;
import com.cashrich.cashrichapp.model.TokenType;
import com.cashrich.cashrichapp.repo.AuthResponseDto;
import com.cashrich.cashrichapp.repo.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	private  UserRepository userRepo;
    
    @Autowired
    private JwtTokenUtil jwtTokenGenerator;
    
    String accessToken;
    
    
    public AuthResponseDto getJwtTokensAfterAuthentication1(JwtRequest jwtRequest) {
    	try {
    		
    		
    		
    	    var userInfoEntity = userRepo.findByUsername(jwtRequest.getUsername())
    	            .orElseThrow(new Supplier<ResponseStatusException>() {
    	                @Override
    	                public ResponseStatusException get() {   	                   
    	                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    	                }
    	            });

    	    accessToken = jwtTokenGenerator.generateToken(jwtRequest.getUsername());
    	  
    	    return new AuthResponseDto.Builder()
    	            .accessToken(accessToken)
    	            .accessTokenExpiry(15 * 60)
    	            .userName(userInfoEntity.getUsername()) // Assuming you have a method to get the username from userInfoEntity
    	            .userRole(userInfoEntity.getRole())
    	            .tokenType(TokenType.Bearer)
    	            .build();

    	} catch (Exception e) {
    	    System.out.println("[AuthService:userSignInAuth] Exception while authenticating the user due to: " + e.getMessage());
    	    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please try again");
    	}
    }
	
    
    

}
