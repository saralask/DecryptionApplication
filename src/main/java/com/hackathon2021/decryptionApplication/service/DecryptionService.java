package com.hackathon2021.decryptionApplication.service;

import com.hackathon2021.decryptionApplication.domain.Feature;
import com.hackathon2021.decryptionApplication.domain.NodeDetails;
import com.hackathon2021.decryptionApplication.domain.Product;
import com.hackathon2021.decryptionApplication.repository.FeatureRepository;
import com.hackathon2021.decryptionApplication.repository.NodeDetailsRepository;
import com.hackathon2021.decryptionApplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import util.ClientMacAddress;
import util.FileOperations;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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

    static int productId = 0;

    public boolean readLicense(MultipartFile multipartFile) throws IOException, ParseException {

        File file = FileOperations.convertToFile(multipartFile);
        String content = FileOperations.parseFile(file);
        List<Feature> features = getFeatures(content.split("\n"));
        String macAddress = ClientMacAddress.findMacAddressOfLocal();
        NodeDetails nodeDetails = new NodeDetails(macAddress);
        if (!nodeDetailsRepository.existsById(macAddress)) {
            Product product = new Product(productId, features, nodeDetails);
            productRepository.save(product);
            return true;
        }
        return false;
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
}
