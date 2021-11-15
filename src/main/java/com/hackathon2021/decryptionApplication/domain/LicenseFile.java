package com.hackathon2021.decryptionApplication.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private byte[] bytes;

    @OneToOne(cascade = CascadeType.ALL)
    private LicenseKey licenseKey;

    public LicenseFile(byte[] bytes, LicenseKey licenseKey) {
        this.bytes = bytes;
        this.licenseKey = licenseKey;
    }
}
