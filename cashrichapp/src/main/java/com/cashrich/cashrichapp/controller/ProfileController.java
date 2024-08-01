package com.cashrich.cashrichapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cashrich.cashrichapp.model.Profile;
import com.cashrich.cashrichapp.model.User;
import com.cashrich.cashrichapp.repo.UserRepository;
import com.cashrich.cashrichapp.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {
	
	
	@Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepo;
    
    @PostMapping("/create")
    public ResponseEntity<?> createProfile(@RequestBody Profile profile) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepo.findByUsername(userDetails.getUsername());
        profileService.saveProfile(profile, user.get());
        return ResponseEntity.ok("Profile created successfully");
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody Profile profile) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepo.findByUsername(userDetails.getUsername());
        profile.setUser(user.get());
        profileService.updateProfile(profile);
        return ResponseEntity.ok("Profile updated successfully");
    }
	

}
