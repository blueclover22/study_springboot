package com.study.springboot.repository;

import com.study.springboot.domain.Pds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdsRepository extends JpaRepository<Pds, Long> {
}
