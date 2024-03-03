package com.company.carbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class License {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    private String licenseNumber;
    private String licenseState;
    private String licenseExpiryDate;
    @JsonIgnore
    @OneToOne(cascade = jakarta.persistence.CascadeType.ALL)
    private Driver driver;

    public License() {
    }

    public License(Long id, String licenseNumber, String licenseState, String licenseExpiryDate, Driver driver) {
        this.id = id;
        this.licenseNumber = licenseNumber;
        this.licenseState = licenseState;
        this.licenseExpiryDate = licenseExpiryDate;
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseState() {
        return licenseState;
    }

    public void setLicenseState(String licenseState) {
        this.licenseState = licenseState;
    }

    public String getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(String licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
