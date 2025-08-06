package com.study.springboot.repository;

import com.study.springboot.domain.CodeDetail;
import com.study.springboot.domain.CodeDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, CodeDetailId> {

    @Query("select max(sortSeq) from CodeDetail where groupCode = ?1")
    public List<Object[]> getMaxSortSeq(String groupCode);
}
