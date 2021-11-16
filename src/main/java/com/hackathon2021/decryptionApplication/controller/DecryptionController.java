package com.hackathon2021.decryptionApplication.controller;

import com.hackathon2021.decryptionApplication.service.DecryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;


@RestController
public class DecryptionController {

    @Autowired
    DecryptionService decryptionService;

    @PostMapping("/uploadLicense")
    public void readLicense(@RequestBody MultipartFile multipartFile, HttpServletRequest request) throws IOException, ParseException {
        decryptionService.readLicense(multipartFile);
    }

    @GetMapping("/getLicenseFile/{productId}")
    public ResponseEntity<ByteArrayResource> getLicenseFile(@PathVariable int productId) throws IOException {

        File file = decryptionService.getLicenseFile(productId);
        Path path = Paths.get("D:\\WORK\\Hackathon2021\\decryptionApplication\\src\\main\\resources" + "/" + file.getName());
        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(MediaType.MULTIPART_FORM_DATA) //
                .contentLength(data.length) //
                .body(resource);
    }
}