package com.study.springboot.controller;


import com.study.springboot.domain.CodeDetail;
import com.study.springboot.service.CodeDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/codeDetails")
public class CodeDetailController {

    private final CodeDetailService service;

    @PostMapping
    public ResponseEntity<CodeDetail> register(@Validated @RequestBody CodeDetail codeDetail) throws Exception {

        log.debug("CodeDetailController.register");

        service.register(codeDetail);
        return new ResponseEntity<>(codeDetail, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CodeDetail>> list() throws Exception {

        log.debug("CodeDetailController.list");

        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @GetMapping("/{groupCode}/{codeValue}")
    public ResponseEntity<CodeDetail> read(@PathVariable("groupCode") String groupCode,
                                           @PathVariable("codeValue") String codeValue) throws Exception {

        log.debug("CodeDetailController.read");

        CodeDetail codeDetail = new CodeDetail();
        codeDetail.setGroupCode(groupCode);
        codeDetail.setCodeValue(codeValue);

        return new ResponseEntity<>(service.read(codeDetail), HttpStatus.OK);
    }

    @PutMapping("/{groupCode}/{codeValue}")
    public ResponseEntity<CodeDetail> modify(@PathVariable("groupCode") String groupCode,
                                             @PathVariable("codeValue") String codeValue,
                                             @Validated @RequestBody CodeDetail codeDetail) throws Exception {

        log.debug("CodeDetailController.modify");

        codeDetail.setGroupCode(groupCode);
        codeDetail.setCodeValue(codeValue);
        service.modify(codeDetail);
        return new ResponseEntity<>(codeDetail, HttpStatus.OK);
    }

    @DeleteMapping("/{groupCode}/{codeValue}")
    public ResponseEntity<Void> remove(@PathVariable("groupCode") String groupCode,
                                       @PathVariable("codeValue") String codeValue) throws Exception {

        log.debug("CodeDetailController.remove");

        CodeDetail codeDetail = new CodeDetail();
        codeDetail.setGroupCode(groupCode);
        codeDetail.setCodeValue(codeValue);
        service.remove(codeDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
