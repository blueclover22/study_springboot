package com.study.springboot.service;

import java.util.List;

import com.study.springboot.domain.ChargeCoin;

public interface CoinService {
    public void charge(ChargeCoin chargeCoin) throws Exception;

    public List<ChargeCoin> list(Long userNo) throws Exception;
}
