package com.study.springboot.controller;

import com.study.springboot.domain.Notice;
import com.study.springboot.service.NoticeService;
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
@RequestMapping("/notices")
public class NoticeController {

    private final NoticeService service;

    @GetMapping("/{noticeNo}")
    public ResponseEntity<Notice> read(@PathVariable("noticeNo") Long noticeNo) throws Exception {

        log.debug("NoticeController.read");

        return new ResponseEntity<>(service.read(noticeNo), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Notice>> list() throws Exception {

        log.debug("NoticeController.list");

        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Notice> register(@Validated @RequestBody Notice notice) throws Exception {

        log.debug("NoticeController.register");

        service.register(notice);
        return new ResponseEntity<>(notice, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{noticeNo}")
    public ResponseEntity<Void> remove(@PathVariable("noticeNo") Long noticeNo) throws Exception {

        log.debug("NoticeController.remove");

        service.remove(noticeNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{noticeNo}")
    public ResponseEntity<Notice> modify(@PathVariable("noticeNo") Long noticeNo,
                                         @Validated @RequestBody Notice notice) throws Exception {

        log.debug("NoticeController.modify");

        notice.setNoticeNo(noticeNo);
        service.modify(notice);
        return new ResponseEntity<>(notice, HttpStatus.OK);
    }
}
