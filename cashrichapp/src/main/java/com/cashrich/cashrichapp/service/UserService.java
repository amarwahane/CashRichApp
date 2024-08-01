package com.cashrich.cashrichapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cashrich.cashrichapp.model.UserDetail;
import com.cashrich.cashrichapp.repo.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	
	 @Autowired
	 private UserRepository userRepo;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		 return userRepo
	                .findByUsername(username)
	                .map(usr -> new UserDetail(usr))
	                .orElseThrow(()-> new UsernameNotFoundException("UserEmail: "+username+" does not exist"));
	}




	 
	 
	

}
