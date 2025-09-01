package com.study.springboot.service;

import com.study.springboot.domain.Item;
import com.study.springboot.domain.Member;
import com.study.springboot.domain.UserItem;

import java.util.List;

public interface UserItemService {

    public void register(Member member, Item item) throws Exception;

    public UserItem read(Long userItemNo) throws Exception;

    public List<UserItem> list(Long userNo) throws Exception;
}
