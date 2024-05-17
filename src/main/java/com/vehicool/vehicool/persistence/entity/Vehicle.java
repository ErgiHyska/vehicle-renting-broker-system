package com.vehicool.vehicool.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="vin",nullable = false)
    private String vin;

    @Column(name="color",nullable = false)
    private String color;

    @Column(name="vehicle_type",nullable = false)
    private String type;

    @Column(name="brand",nullable = false)
    private String Brand;

    @Column(name="model",nullable = false)
    private String model;

    @Column(name="transmission_type",nullable = false)
    private String transmissionType;

    @Column(name="engine_type",nullable = false)
    private String engineType;

    @Column(name="engine_size",nullable = false)
    private Double engineSize;

    @Column(name="no_of_seats",nullable = false)
    private Long noOfSeats;

    @Column(name="status",nullable = false)
    private String status;

    @Column(name="available",nullable = false)
    private Boolean available;

    @Column(name="production_year",nullable = false)
    private Long productionYear;

    @OneToMany(mappedBy ="vehicle")
    private List<Contract> contractSigned;

    @OneToMany(mappedBy ="vehicle")
    private List<ConfidentialFile> vehicleRegistrations;

    @OneToMany(mappedBy ="vehicle")
    private List<FileData> images;

}
