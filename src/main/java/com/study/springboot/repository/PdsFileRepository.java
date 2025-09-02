package com.study.springboot.repository;

import com.study.springboot.domain.PdsFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PdsFileRepository extends JpaRepository <PdsFile, Long>{

    public List<PdsFile> findByFullName(String fullName);
}
