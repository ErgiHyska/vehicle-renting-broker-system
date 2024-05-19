package com.vehicool.vehicool.business.service;

import com.querydsl.core.types.Predicate;
import com.vehicool.vehicool.business.querydsl.VehicleFilter;
import com.vehicool.vehicool.business.querydsl.VehicleQueryDsl;
import com.vehicool.vehicool.persistence.entity.FileData;
import com.vehicool.vehicool.persistence.entity.Vehicle;
import com.vehicool.vehicool.persistence.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleQueryDsl vehicleQueryDsl;
    private final String VEHICLE_IMAGES_PATH = "C:\\Users\\ergih\\Desktop\\vehicleImages\\";

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void delete(Long id) {
        vehicleRepository.deleteById(id);
    }

    public Vehicle update(Vehicle vehicle, Long Id) {
        vehicle.setId(Id);
        return vehicleRepository.saveAndFlush(vehicle);
    }

    public Page<Vehicle> findAll(VehicleFilter vehicleFilter, Pageable pageRequest) {
        Predicate filter = vehicleQueryDsl.filter(vehicleFilter);
        return vehicleRepository.findAll(filter, pageRequest);
    }
}
