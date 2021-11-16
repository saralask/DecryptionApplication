package com.hackathon2021.decryptionApplication.service;

import com.hackathon2021.decryptionApplication.domain.*;
import com.hackathon2021.decryptionApplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import util.ClientMacAddress;
import util.FileOperations;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DecryptionService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    FeatureRepository featureRepository;

    @Autowired
    NodeDetailsRepository nodeDetailsRepository;

    @Autowired
    LicenseFileRepository licenseFileRepository;

    @Autowired
    LicenseKeyRepository licenseKeyRepository;

    static int productId = 0;

    public void readLicense(MultipartFile multipartFile) throws IOException, ParseException {

        String license_key_contents = "dummy_key";

        File file = FileOperations.convertToFile(multipartFile);
        String content = FileOperations.parseFile(file);
        List<Feature> features = getFeatures(content.split("\n"));
        String macAddress = "nodeAddress " + ClientMacAddress.findMacAddressOfLocal();
        content = content + macAddress;
        NodeDetails nodeDetails = new NodeDetails(macAddress);

        File license_key = new File(license_key_contents);

        if (!nodeDetailsRepository.existsById(macAddress)) {
            LicenseKey licenseKey = new LicenseKey(license_key.getName().getBytes());
            licenseKeyRepository.save(licenseKey);
            LicenseFile licenseFile = new LicenseFile(content.getBytes(), licenseKey);
            licenseFileRepository.save(licenseFile);
            Product product = new Product(productId, features, nodeDetails, licenseFile);
            productRepository.save(product);
        }
    }


    List<Feature> getFeatures(String[] lines) throws ParseException {
        List<Feature> features = new ArrayList<>();

        for (String line : lines) {
            String[] fileContents = line.split(" ");
            productId = Integer.parseInt(fileContents[0]);
            String featureName = fileContents[1];
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fileContents[2]);
            int seatsReserved = Integer.parseInt(fileContents[3].trim());

            if (featureRepository.findById(featureName).isPresent()) {
                Feature feature = featureRepository.getById(featureName);
                feature.setNumberOfSeatsUsed(feature.getNumberOfSeatsUsed() + 1);
                featureRepository.save(feature);
            } else {
                Feature feature = new Feature(featureName, date, seatsReserved, 1);
                features.add(feature);
            }
        }

        return features;
    }

    public void getClientIp(HttpServletRequest request) {
        ClientMacAddress.findMacAddressOfLocal();
    }


    public File getLicenseFile(int productId) {
        LicenseFile licenseFile = productRepository.getById(productId).getLicenseFile();
        LicenseKey licenseKey = productRepository.getById(productId).getLicenseFile().getLicenseKey();

        String licenseFilepath = "D:\\WORK\\Hackathon2021\\decryptionApplication\\src\\main\\resources\\licenseFile.txt";
        try (FileOutputStream fos = new FileOutputStream(licenseFilepath)) {
            fos.write(licenseFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String licenseKeypath = "D:\\WORK\\Hackathon2021\\decryptionApplication\\src\\main\\resources\\licenseKey.txt";
        try (FileOutputStream fos = new FileOutputStream(licenseKeypath)) {
            fos.write(licenseKey.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileOperations.convertToZip(licenseFilepath, licenseKeypath);
    }
}
