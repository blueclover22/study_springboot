package com.study.springboot.controller;


import com.study.springboot.domain.CodeDetail;
import com.study.springboot.service.CodeDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/codeDetails")
public class CodeDetailController {

    private final CodeDetailService service;

    @PostMapping
    public ResponseEntity<CodeDetail> register(@Validated @RequestBody CodeDetail codeDetail) throws Exception {

        service.register(codeDetail);

        return new ResponseEntity<>(codeDetail, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CodeDetail>> list() throws Exception {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }
}
