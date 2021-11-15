package com.hackathon2021.decryptionApplication.repository;

import com.hackathon2021.decryptionApplication.domain.NodeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeDetailsRepository extends JpaRepository<NodeDetails, String> {
}
