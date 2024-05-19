package com.vehicool.vehicool.business.querydsl;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleFilter {
    private String color;
    private String type;
    private String Brand;
    private String model;
    private String transmissionType;
    private String engineType;
    private Double engineSize;
    private Long noOfSeats;
    private String status;
    private Boolean available;
    private Long productionYear;
    private Long locationId;
}