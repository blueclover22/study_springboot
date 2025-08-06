package com.study.springboot.controller;


import com.study.springboot.service.CodeDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/codeDetails")
public class CodeDetailController {

    private final CodeDetailService service;
}
