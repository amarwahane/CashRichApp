package com.cashrich.cashrichapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cashrich.cashrichapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	 Optional<User> findByUsername(String username);

}
