package com.study.springboot.common.cotroller;

import com.study.springboot.dto.CodeLabelValue;
import com.study.springboot.service.CodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/codes")
public class CodeController {

    private final CodeService service;

    @GetMapping("/codeGroup")
    public ResponseEntity<List<CodeLabelValue>> getCodeGroupList() throws Exception {

        log.debug("CodeController.getCodeGroupList");

        return new ResponseEntity<>(service.getCodeGroupList(), HttpStatus.OK);
    }

    @GetMapping("/job")
    public ResponseEntity<List<CodeLabelValue>> getJobList() throws Exception {

        log.debug("CodeController.getJobList");

        String classCode = "A01";

        List<CodeLabelValue> jobList = service.getCodeList(classCode);

        return new ResponseEntity<>(jobList, HttpStatus.OK);
    }
}
