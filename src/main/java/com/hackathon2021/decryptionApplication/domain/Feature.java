package com.hackathon2021.decryptionApplication.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feature {

    @Id
    private String featureName;

    private Date expiryDate;

    private int totalNumOfSeatsLicensed;

    private int numberOfSeatsUsed = 0;
}
