package com.study.springboot.repository;

import com.study.springboot.domain.CodeDetail;
import com.study.springboot.domain.CodeDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, CodeDetailId> {

    @Query("select max(cd.sortSeq) from CodeDetail cd where cd.groupCode = ?1")
    public Integer getMaxSortSeq(String groupCode);

    @Query("select cd from CodeDetail cd where cd.groupCode = ?1 order by cd.sortSeq asc")
    public List<CodeDetail> getCodeList(String groupCode);
}
