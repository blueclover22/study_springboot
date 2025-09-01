package com.study.springboot.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.springboot.domain.Item;
import com.study.springboot.prop.ShopProperties;
import com.study.springboot.service.ItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ShopProperties shopProperties;

    @GetMapping
    public ResponseEntity<List<Item>> list() throws Exception {
        return new ResponseEntity<>(itemService.list(), HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> read(@PathVariable("itemId") Long itemId) throws Exception {
        return new ResponseEntity<>(itemService.read(itemId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Item> register(@RequestPart("item") String itemString,
            @RequestPart("file") MultipartFile originalImageFile,
            @RequestPart("file2") MultipartFile previewImageFile) throws Exception {

        Item item = new ObjectMapper().readValue(itemString, Item.class);

        String itemName = item.getItemName();
        String description = item.getDescription();

        if (itemName != null) {
            item.setItemName(itemName);
        }

        if (description != null) {
            item.setDescription(description);
        }

        item.setPicture(originalImageFile);
        item.setPreview(previewImageFile);

        MultipartFile pictureFile = item.getPicture();
        MultipartFile previewFile = item.getPreview();

        if (pictureFile != null) {
            log.info("pictureFile.getName() : {}", pictureFile.getName());
        } else {
            log.info("pictureFile is null");
        }

        String createdPictureFileName = null;
        if (pictureFile != null) {
            createdPictureFileName = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
            item.setPictureUrl(createdPictureFileName);
        } else {
            item.setPictureUrl(null);
        }

        String createdPreviewFileName = null;
        if (previewFile != null) {
            createdPreviewFileName = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());
            item.setPreviewUrl(createdPreviewFileName);
        } else {
            item.setPreviewUrl(null);
        }

        itemService.register(item);

        Item createdItem = new Item();
        createdItem.setItemId(item.getItemId());

        return new ResponseEntity<>(createdItem, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{itemId}")
    public ResponseEntity<Item> modify(@PathVariable("itemId") Long itemId,
            @RequestPart("item") String itemString,
            @RequestPart(value = "file", required = false) MultipartFile originalImageFile,
            @RequestPart(value = "file2", required = false) MultipartFile previewImageFile) throws Exception {

        Item item = new ObjectMapper().readValue(itemString, Item.class);
        item.setItemId(itemId);

        String itemName = item.getItemName();
        String description = item.getDescription();

        if (itemName != null) {
            item.setItemName(itemName);
        }

        if (description != null) {
            item.setDescription(description);
        }

        item.setPicture(originalImageFile);
        item.setPreview(previewImageFile);

        MultipartFile pictureFile = item.getPicture();
        MultipartFile previewFile = item.getPreview();

        if (pictureFile != null && pictureFile.getSize() > 0) {
            String createdPictureFileName = uploadFile(pictureFile.getOriginalFilename(), pictureFile.getBytes());
            item.setPictureUrl(createdPictureFileName);
        } else {
            Item oldItem = itemService.read(itemId);
            item.setPictureUrl(oldItem.getPictureUrl());
        }

        if (previewFile != null && previewFile.getSize() > 0) {
            String createdPreviewFileName = uploadFile(previewFile.getOriginalFilename(), previewFile.getBytes());
            item.setPreviewUrl(createdPreviewFileName);
        } else {
            Item oldItem = itemService.read(itemId);
            item.setPreviewUrl(oldItem.getPreviewUrl());
        }

        itemService.modify(item);

        Item modifiedItem = new Item();
        modifiedItem.setItemId(item.getItemId());

        return new ResponseEntity<>(modifiedItem, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> remove(@PathVariable("itemId") Long itemId) throws Exception {
        itemService.remove(itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> displayFile(@RequestParam("itemId") Long itemId) throws Exception {

        ResponseEntity<byte[]> responseEntity = null;

        String fileName = itemService.getPicture(itemId);

        try {
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

            MediaType mediaType = getMediaType(formatName);

            HttpHeaders headers = new HttpHeaders();

            File file = new File(shopProperties.getUploadPath() + File.separator + fileName);

            if (mediaType != null) {
                headers.setContentType(mediaType);
            }

            responseEntity = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @GetMapping("/preview")
    public ResponseEntity<byte[]> previewFile(@RequestParam("itemId") Long itemId) throws Exception {

        ResponseEntity<byte[]> responseEntity = null;

        String fileName = itemService.getPreview(itemId);

        try {
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

            MediaType mediaType = getMediaType(formatName);

            HttpHeaders headers = new HttpHeaders();

            File file = new File(shopProperties.getUploadPath() + File.separator + fileName);

            if (mediaType != null) {
                headers.setContentType(mediaType);
            }

            responseEntity = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/download/{itemId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("itemId") Long itemId) throws Exception {
        ResponseEntity<byte[]> responseEntity = null;

        String fullName = itemService.getPicture(itemId);

        try {
            HttpHeaders headers = new HttpHeaders();

            File file = new File(shopProperties.getUploadPath() + File.separator + fullName);

            String fileName = fullName.substring(fullName.indexOf("_") + 1);

            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            headers.add("Content-Disposition",
                    "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO_8859_1") + "\"");

            responseEntity = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    private String uploadFile(String fileName, byte[] fileBytes) throws Exception {
        UUID uid = UUID.randomUUID();

        String createdFileName = uid.toString() + "_" + fileName;

        File target = new File(shopProperties.getUploadPath(), createdFileName);

        FileCopyUtils.copy(fileBytes, target);

        return createdFileName;
    }

    private MediaType getMediaType(String formatName) {

        if (formatName != null) {
            if (formatName.equals("JPG")) {
                return MediaType.IMAGE_JPEG;
            }
            if (formatName.equals("GIF")) {
                return MediaType.IMAGE_GIF;
            }
            if (formatName.equals("PNG")) {
                return MediaType.IMAGE_PNG;
            }
        }
        return null;
    }

}
