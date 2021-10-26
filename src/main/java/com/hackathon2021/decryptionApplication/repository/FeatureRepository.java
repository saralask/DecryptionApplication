package com.hackathon2021.decryptionApplication.repository;

import com.hackathon2021.decryptionApplication.domain.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, String> {

}
