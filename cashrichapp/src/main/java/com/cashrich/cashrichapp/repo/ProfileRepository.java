package com.cashrich.cashrichapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cashrich.cashrichapp.model.Profile;


@Repository
public interface ProfileRepository  extends JpaRepository<Profile,Long>{

}
