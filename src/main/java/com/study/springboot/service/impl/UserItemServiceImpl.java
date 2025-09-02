package com.study.springboot.service.impl;

import com.study.springboot.common.exception.NotEnoughCoinException;
import com.study.springboot.domain.Item;
import com.study.springboot.domain.Member;
import com.study.springboot.domain.PayCoin;
import com.study.springboot.domain.UserItem;
import com.study.springboot.repository.MemberRepository;
import com.study.springboot.repository.PayCoinRepository;
import com.study.springboot.repository.UserItemRepository;
import com.study.springboot.service.UserItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserItemServiceImpl implements UserItemService {

    private final UserItemRepository userItemRepository;

    private final PayCoinRepository payCoinRepository;

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void register(Member member, Item item) throws Exception {

        Long userNo = member.getUserNo();

        Long itemId = item.getItemId();
        int price = item.getPrice();

        UserItem userItem = new UserItem();

        userItem.setUserNo(userNo);
        userItem.setItemId(itemId);

        PayCoin payCoin = new PayCoin();
        payCoin.setUserNo(userNo);
        payCoin.setAmount(price);


        Member memberEntity = memberRepository.getReferenceById(userNo);

        int coin = memberEntity.getCoin();
        int amount = payCoin.getAmount();

        if (coin < price) {
            throw new NotEnoughCoinException("The coin is not enough.");
        }

        memberEntity.setCoin(coin - amount);

        memberRepository.save(memberEntity);
        userItemRepository.save(userItem);
        payCoinRepository.save(payCoin);

    }

    @Override
    public UserItem read(Long userItemNo) throws Exception {
        return null;
    }

    @Override
    public List<UserItem> list(Long userNo) throws Exception {
        return List.of();
    }
}
