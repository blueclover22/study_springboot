package com.study.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.springboot.domain.ChargeCoin;

public interface ChargeCoinRepository extends JpaRepository<ChargeCoin, Long>{
    
}
