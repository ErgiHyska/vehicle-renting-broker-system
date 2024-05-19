package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.VehicleDTO;
import com.vehicool.vehicool.business.querydsl.VehicleFilter;
import com.vehicool.vehicool.business.service.DataPoolService;
import com.vehicool.vehicool.business.service.StorageService;
import com.vehicool.vehicool.business.service.VehicleService;
import com.vehicool.vehicool.persistence.entity.Vehicle;
import com.vehicool.vehicool.util.mappers.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import static com.vehicool.vehicool.util.constants.Messages.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/vehicle")
public class VehicleController {

    private final ModelMapper modelMapper;
    private final VehicleService vehicleService;
    private final DataPoolService dataPoolService;
    private final StorageService storageService;


    @GetMapping("/list")
    @Transactional
    public ResponseEntity<Object> list(@Valid @RequestParam Map<String, Object> vehicleFilterRequest,
                                       @RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       @RequestParam(defaultValue = "id") String sort) {
        try {
            VehicleFilter companyFilter = modelMapper.map(vehicleFilterRequest, VehicleFilter.class);
            Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
            Page<Vehicle> vehiclePage = vehicleService.findAll(companyFilter, pageRequest);

            return ResponseMapper.map(SUCCESS, HttpStatus.OK, vehiclePage, RECORDS_RECEIVED);
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id)  {
        try {
            Vehicle vehicle = vehicleService.getVehicleById(id);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, vehicle, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
//    @GetMapping("/{id}/images")
//    public ResponseEntity<?> downloadImageFromFileSystem(@PathVariable Long id) throws IOException {
//        List<byte[]> images = vehicleService.downloadVehiclePicturesById(id);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png")).body(images.get(0));
//    }
}
