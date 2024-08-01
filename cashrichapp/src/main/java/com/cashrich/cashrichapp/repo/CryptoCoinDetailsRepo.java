package com.cashrich.cashrichapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cashrich.cashrichapp.model.CryptoCoinDetails;

@Repository
public interface CryptoCoinDetailsRepo extends JpaRepository<CryptoCoinDetails, Long>{

}
