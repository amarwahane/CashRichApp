package com.cashrich.cashrichapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cashrich.cashrichapp.service.ApiService;

@RestController
@RequestMapping("/crypto")
public class CryptoApiController {
	
	 @Autowired
	    private ApiService apiService;
	 
	 
	 @GetMapping("/coin")
	    public ResponseEntity<?> getCryptoData() {
	        String data = apiService.getCryptoData();
	        return ResponseEntity.ok(data);
	    }

}
