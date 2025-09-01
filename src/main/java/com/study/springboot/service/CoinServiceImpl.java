package com.study.springboot.service;   

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.springboot.domain.ChargeCoin;
import com.study.springboot.domain.Member;
import com.study.springboot.repository.ChargeCoinRepository;
import com.study.springboot.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoinServiceImpl implements CoinService {

    private final ChargeCoinRepository chargeCoinRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void charge(ChargeCoin chargeCoin) throws Exception {
        Member memberEntity = memberRepository.getReferenceById(chargeCoin.getUserNo());

        int coin = memberEntity.getCoin();
        int amount = chargeCoin.getAmount();

        memberEntity.setCoin(coin + amount);
        memberRepository.save(memberEntity);
        chargeCoinRepository.save(chargeCoin);
    }

    @Override
    public List<ChargeCoin> list(Long userNo) throws Exception {
        return chargeCoinRepository.findAll(Sort.by(Sort.Direction.DESC, "historyNo"));
    }
}
