package com.study.springboot.controller;

import com.study.springboot.domain.ChargeCoin;
import com.study.springboot.domain.CustomUser;
import com.study.springboot.domain.PayCoin;
import com.study.springboot.service.CoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/coins")
public class CoinController {

    private final CoinService service;

    private final MessageSource messageSource;

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping(value = "/charge/{amount}", produces = "text/plain; charset=utf8")
    public ResponseEntity<String> charge(@PathVariable("amount") int amount,
                                         @AuthenticationPrincipal CustomUser customUser) throws Exception {

        log.debug("CoinController.charge");

        Long userNo = customUser.getUserNo();

        ChargeCoin chargeCoin = new ChargeCoin();
        chargeCoin.setUserNo(userNo);
        chargeCoin.setAmount(amount);

        service.charge(chargeCoin);

        String message = messageSource.getMessage("coin.chargingComplete", null, Locale.KOREAN);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping
    public ResponseEntity<List<ChargeCoin>> list(@AuthenticationPrincipal CustomUser customUser) throws Exception {

        log.debug("CoinController.list");

        Long userNo = customUser.getUserNo();
        return new ResponseEntity<>(service.list(userNo), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/pay")
    public ResponseEntity<List<PayCoin>> listPayHistory(@AuthenticationPrincipal CustomUser customUser) throws Exception {

        log.debug("CoinController.listPayHistory");

        Long userNo = customUser.getUserNo();
        return new ResponseEntity<>(service.listPayHistory(userNo), HttpStatus.OK);

    }

}
