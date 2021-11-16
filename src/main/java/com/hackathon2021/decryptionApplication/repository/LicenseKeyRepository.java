package com.hackathon2021.decryptionApplication.repository;

import com.hackathon2021.decryptionApplication.domain.LicenseKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseKeyRepository extends JpaRepository<LicenseKey, Integer> {
}
