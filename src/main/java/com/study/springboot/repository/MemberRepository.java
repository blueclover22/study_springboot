package com.study.springboot.repository;

import com.study.springboot.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public List<Member> findByUserId(String userId);

    //목록 조회
    @Query("select m.userNo, m.userId, m.userPw, m.userName, cd.codeName, m.coin, m.regDate"
        + " from Member m"
        + " inner join CodeDetail cd on cd.codeValue = m.job"
        + " inner join CodeGroup cg on cg.groupCode = cd.groupCode"
        + " where cg.groupCode = 'A01'"
        + " order by m.regDate desc")
    public List<Object[]> listAllMember();
}
