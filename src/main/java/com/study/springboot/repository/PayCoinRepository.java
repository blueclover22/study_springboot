package com.study.springboot.repository;

import com.study.springboot.domain.PayCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PayCoinRepository extends JpaRepository<PayCoin, Long> {

    @Query("select a.historyNo, a.userNo, a.itemId, b.itemName, a.amount, a.regDate "
        + "from PayCoin a inner join Item b on a.itemId = b.itemId "
        + "where a.userNo = ?1 and a.historyNo > 0 "
        + "order by a.historyNo desc")
    public List<Object[]> listPayHistory(Long userNo);
}
