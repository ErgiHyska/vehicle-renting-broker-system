package com.vehicool.vehicool.api.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleDTO {

    @NotNull(message = "VIN is required!")
    private String vin;

    @NotNull(message = "Color is required!")
    private String color;

    @NotNull(message = "Type is required!")
    private String type;

    @NotNull(message = "Brand is required!")
    private String Brand;

    @NotNull(message = "Model is required!")
    private String model;

    @NotNull(message = "Transmission Type is required!")
    private String transmissionType;

    @NotNull(message = "Engine Type is required!")
    private String engineType;

    @NotNull(message = "Vat ID is required!")
    private Double engineSize;

    @NotNull(message = "Number is required!")
    private Long noOfSeats;

    @NotNull(message = "Production Year is required!")
    private Long productionYear;

}
