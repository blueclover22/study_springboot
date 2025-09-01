package com.study.springboot.controller;

import com.study.springboot.domain.CodeGroup;
import com.study.springboot.service.CodeGroupService;
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
@RequestMapping("/codeGroups")
public class CodeGroupController {

    private final CodeGroupService service;

    @PostMapping
    public ResponseEntity<CodeGroup> register(@Validated @RequestBody CodeGroup codeGroup) throws Exception {
        service.register(codeGroup);
        return new ResponseEntity<>(codeGroup, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CodeGroup>> list() throws Exception {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @GetMapping("/{groupCode}")
    public ResponseEntity<CodeGroup> read(@PathVariable("groupCode") String groupCode) throws Exception {
        return new ResponseEntity<>(service.read(groupCode), HttpStatus.OK);
    }

    @PutMapping("/{groupCode}")
    public ResponseEntity<CodeGroup> modify(@PathVariable("groupCode") String groupCode,
                                            @Validated @RequestBody CodeGroup codeGroup) throws Exception {
        codeGroup.setGroupCode(groupCode);
        service.modify(codeGroup);
        return new ResponseEntity<>(codeGroup, HttpStatus.OK);
    }

    @DeleteMapping("/{groupCode}")
    public ResponseEntity<Void> remove(@PathVariable("groupCode") String groupCode) throws Exception {
        service.remove(groupCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
