package com.vehicool.vehicool.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "data_pool")
public class DataPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enum_label",nullable = false)
    private String enumLabel;

    @Column(name = "enum_name",nullable = false)
    private String enumName;

    @OneToMany(mappedBy = "enumLabel")
    @JsonBackReference
    private List<TranslatedDataPool> translatedDataPoolList;

    @OneToMany(mappedBy = "contractualStatus", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Contract> contractList;

    @OneToMany(mappedBy = "status", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Vehicle> statusVehicles;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Vehicle> locationVehicles;

    @OneToMany(mappedBy = "engineType", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Vehicle> engineTypeVehicles;

    @OneToMany(mappedBy = "transmissionType", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Vehicle> transmissionVehicles;
}
