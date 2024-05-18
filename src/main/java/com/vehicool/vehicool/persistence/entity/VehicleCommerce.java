package com.vehicool.vehicool.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vehicle_commerce")
public class VehicleCommerce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "vehicleId")
    private Vehicle vehicle;

    @Column(name = "date_available")
    private Double pricePerDay;

    @Column(name = "maxim_date_available")
    private Date dateAvailable;

    @Column(name = "price_per_day")
    private Date maxDateAvailable;
}
