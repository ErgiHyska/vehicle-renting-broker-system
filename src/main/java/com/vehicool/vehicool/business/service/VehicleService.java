package com.vehicool.vehicool.business.service;

import com.querydsl.core.types.Predicate;
import com.vehicool.vehicool.business.querydsl.VehicleFilter;
import com.vehicool.vehicool.business.querydsl.VehicleQueryDsl;
import com.vehicool.vehicool.persistence.entity.FileData;
import com.vehicool.vehicool.persistence.entity.Vehicle;
import com.vehicool.vehicool.persistence.repository.FileDataRepository;
import com.vehicool.vehicool.persistence.repository.StorageRepository;
import com.vehicool.vehicool.persistence.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final StorageRepository repository;
    private final FileDataRepository fileDataRepository;
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

    public String uploadVehicleImageToFileSystem(Long id, MultipartFile file) throws IOException {
        String filePath = VEHICLE_IMAGES_PATH + file.getOriginalFilename();
        Vehicle vehicle = getVehicleById(id);
        FileData fileData = null;
        if (vehicle != null) {
            fileData = fileDataRepository.save(FileData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(filePath)
                    .vehicle(vehicle).build());


            file.transferTo(new File(filePath));
        }
        if (fileData != null) {
            return "file uploaded successfully : " + filePath;
        }
        return null;
    }

    public byte[] downloadVehiclePicture(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }

    public List<byte[]> downloadVehiclePicturesById(Long id) throws IOException {
        Vehicle vehicle = vehicleRepository.getReferenceById(id);
        List<FileData> imageList = vehicle.getImages();
        List<String> paths = imageList.stream().map(FileData::getFilePath).toList();
        List<byte[]> images = new ArrayList<>();
        for (String path : paths) {
            images.add(Files.readAllBytes(new File(path).toPath()));
        }
        return images;
    }
}
