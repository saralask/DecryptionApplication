package com.hackathon2021.decryptionApplication.controller;

import com.hackathon2021.decryptionApplication.service.DecryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@RestController
public class DecryptionController {

    @Autowired
    DecryptionService decryptionService;

    @PostMapping("/uploadLicense")
    public ResponseEntity<?> readLicense(@RequestBody MultipartFile multipartFile, HttpServletRequest request) throws IOException, ParseException {
        if(decryptionService.readLicense(multipartFile))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/isFeatureLicensed")
    public boolean isFeatureLicensed(int featureId){
        return false;
    }

    public Date TimeToExpiry(int featureId){
        return new Date();
    }

}
