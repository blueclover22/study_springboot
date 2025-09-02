package com.study.springboot.controller;

import com.study.springboot.common.util.UploadFileUtils;
import com.study.springboot.domain.Pds;
import com.study.springboot.prop.ShopProperties;
import com.study.springboot.service.PdsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pds")
public class PdsController {

    private final PdsService service;
    private final ShopProperties shopProperties;

    @GetMapping
    public ResponseEntity<List<Pds>> list() throws Exception {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Pds> register(@Validated @RequestBody Pds pds) throws Exception {
        service.register(pds);
        return new ResponseEntity<>(pds, HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Pds> read(@PathVariable("itemId") Long itemId) throws Exception {
        Pds pds = service.read(itemId);
        return new ResponseEntity<>(pds, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{itemId}")
    public ResponseEntity<Pds> modify(@PathVariable("itemId") Long itemId,
                                      @Validated @RequestBody Pds pds) throws Exception {
        pds.setItemId(itemId);
        service.modify(pds);
        return new ResponseEntity<>(pds, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> remove(@PathVariable("itemId") Long itemId) throws Exception {
        service.remove(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value ="/upload", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> upload(MultipartFile file) throws Exception {
        String savedName = UploadFileUtils.uploadFile(shopProperties.getUploadPath(), file.getOriginalFilename(), file.getBytes());

        return new ResponseEntity<>(savedName, HttpStatus.OK);
    }

    @GetMapping("/download}")
    public ResponseEntity<byte[]> download(String fullName) throws Exception {
        ResponseEntity<byte[]> responseEntity = null;

        service.updateAttachDownCnt(fullName);

        try {
            HttpHeaders headers = new HttpHeaders();

            File file = new File(shopProperties.getUploadPath() + File.separator + fullName);

            String fileName = fullName.substring(fullName.lastIndexOf("_") + 1);
            headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
            headers.add("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO_8859_1") + "\"");

            responseEntity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @GetMapping("/attach/{itemId}")
    public List<String> attach(@PathVariable("itemId") Long itemId) throws Exception{
        return service.getAttach(itemId);
    }

}
