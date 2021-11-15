package com.hackathon2021.decryptionApplication.controller;

import com.hackathon2021.decryptionApplication.service.DecryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public void getLicenseFile(@PathVariable int productId) {
        decryptionService.getLicenseFile(productId);
    }
}