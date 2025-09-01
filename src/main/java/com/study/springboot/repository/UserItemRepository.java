package com.study.springboot.repository;

import com.study.springboot.domain.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {

    public List<UserItem> findByUserId(Long userNo);


    @Query("select a.userItemNo, a.userNo, a.itemId, a.regDate, b.itemName, b.price, b.description, b.pictureUrl"
        + " from UserItem a inner join Item b on a.itemId = b.itemId"
        + " where a.userNo = ?1"
        + " order by a.userItemNo desc, a.regDate desc")
    public List<Object[]> listUserItem(Long userNo);

    @Query("select a.userItemNo, a.userNo, a.itemId, a.regDate, b.itemName, b.price, b.description, b.pictureUrl"
        + " from UserItem a inner join Item b on a.itemId = b.itemId"
        + " where a.userItemNo = ?1")
    public List<Object[]> readUserItem(Long userItemNo);
}
