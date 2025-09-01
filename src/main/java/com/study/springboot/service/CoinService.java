package com.study.springboot.service;

import java.util.List;

import com.study.springboot.domain.ChargeCoin;
import com.study.springboot.domain.PayCoin;

public interface CoinService {
    public void charge(ChargeCoin chargeCoin) throws Exception;

    public List<ChargeCoin> list(Long userNo) throws Exception;

    public List<PayCoin> listPayHistory(Long userNo) throws Exception;
}
