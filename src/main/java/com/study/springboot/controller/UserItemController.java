package com.study.springboot.controller;

import com.study.springboot.domain.CustomUser;
import com.study.springboot.domain.UserItem;
import com.study.springboot.prop.ShopProperties;
import com.study.springboot.service.UserItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/userItems")
public class UserItemController {

    private final UserItemService service;

    private final ShopProperties shopProperties;

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping
    public ResponseEntity<List<UserItem>> list(@AuthenticationPrincipal CustomUser customUser) throws Exception {
        Long userNo = customUser.getUserNo();
        return new ResponseEntity<>(service.list(userNo), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/{userItemNo}")
    public ResponseEntity<UserItem> read(@PathVariable("userItemNo") Long userItemNo) throws Exception {
        UserItem userItem = service.read(userItemNo);
        return new ResponseEntity<>(userItem, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/download/{userItemNo}")
    public ResponseEntity<byte[]> download(@PathVariable("userItemNo") Long userItemNo) throws Exception {

        UserItem userItem = service.read(userItemNo);

        String fullName = userItem.getPictureUrl();

        ResponseEntity<byte[]> responseEntity = null;

        try {
            HttpHeaders headers = new HttpHeaders();

            File file = new File(shopProperties.getUploadPath() + File.separator + fullName);

            String fileName = fullName.substring(fullName.indexOf("_") + 1);

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            headers.add("Content-Disposition",
                "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"),
                    "ISO_8859_1") + "\"");

            responseEntity = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;

    }


}
