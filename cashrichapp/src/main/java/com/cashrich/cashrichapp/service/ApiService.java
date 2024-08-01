package com.cashrich.cashrichapp.service;

import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cashrich.cashrichapp.model.CryptoCoinDetails;
import com.cashrich.cashrichapp.model.User;
import com.cashrich.cashrichapp.repo.CryptoCoinDetailsRepo;
import com.cashrich.cashrichapp.repo.UserRepository;

@Service
public class ApiService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CryptoCoinDetailsRepo cryptoRepo;
	
	 private static final String KEY = "27ab17d1-215f-49e5-9ca4-afd48810c149";
	 
	 private static final String URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=BTC,ETH,LTC";
	 
	 
	 
	 public String getCryptoData() {
	        RestTemplate restTemplate = new RestTemplate();

	        HttpHeaders headers = new HttpHeaders();
	        headers.set("X-CMC_PRO_API_KEY", KEY);

	        HttpEntity<String> entity = new HttpEntity<>(headers);
	        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);

	        
	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        User user = userRepo.findByUsername(userDetails.getUsername())
	                .orElseThrow(() -> new RuntimeException("User not found"));
	        
	        CryptoCoinDetails cryptoCoinDetails=new CryptoCoinDetails();
	        
	        cryptoCoinDetails.setUser(user);
	        cryptoCoinDetails.setResponse(response.getBody());
	        cryptoCoinDetails.setTimestamp(LocalDateTime.now());
	        
	        cryptoRepo.save(cryptoCoinDetails);
	        
	        return response.getBody();
	    }
}
