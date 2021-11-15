package com.hackathon2021.decryptionApplication.repository;

import com.hackathon2021.decryptionApplication.domain.LicenseFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseFileRepository extends JpaRepository<LicenseFile, Integer> {
}
