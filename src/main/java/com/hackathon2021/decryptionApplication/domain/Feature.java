package com.hackathon2021.decryptionApplication.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feature {

    @Id
    private int featureId;

    private Date expiryDate;

    private int totalNumOfSeatsLicensed;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<NodeDetails> nodeDetails;
}
