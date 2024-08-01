package com.cashrich.cashrichapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cashrich.cashrichapp.model.Profile;
import com.cashrich.cashrichapp.model.User;
import com.cashrich.cashrichapp.repo.ProfileRepository;

@Service
public class ProfileService {

	 @Autowired
	 private ProfileRepository profileRepository;

	    public Profile saveProfile(Profile profile, User user) {
	    	profile.setUser(user);
	        return profileRepository.save(profile);
	    }

	    public Profile updateProfile(Profile profile) {
	        return profileRepository.save(profile);
	    }
}
