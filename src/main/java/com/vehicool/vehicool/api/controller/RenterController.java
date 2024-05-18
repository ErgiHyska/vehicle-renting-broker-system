package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.RenterDTO;
import com.vehicool.vehicool.business.service.DataPoolService;
import com.vehicool.vehicool.business.service.RenterService;
import com.vehicool.vehicool.business.service.StorageService;
import com.vehicool.vehicool.business.service.VehicleService;
import com.vehicool.vehicool.persistence.entity.Renter;
import com.vehicool.vehicool.persistence.entity.Vehicle;
import com.vehicool.vehicool.util.mappers.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.vehicool.vehicool.util.constants.Messages.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/renter")
public class RenterController {
    private final ModelMapper modelMapper;
    private final RenterService renterService;
    private final DataPoolService dataPoolService;
    private final VehicleService vehicleService;
    private final StorageService storageService;
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid RenterDTO renterDTO) {
        try {
            Renter renter = modelMapper.map(renterDTO, Renter.class);

            renter.setStatus(dataPoolService.getDataPoolById(1l));
            renterService.save(renter);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renter, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id)  {
        try {
            Renter renter = renterService.getRenterById(id);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renter, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            renterService.delete(id);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, null, "Renter deleted successfuly !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid RenterDTO renterDTO) {
        try {
            Renter renter = renterService.getRenterById(id);
            modelMapper.map(renterDTO, renter);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renter, "Renter updated successfuly !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
    @PostMapping("/{id}/rent/{vehicleId}")
    public ResponseEntity<Object> rent(@PathVariable Long id, @PathVariable Long vehicleId){
        try {
            Renter renter = renterService.getRenterById(id);
            if(renter!=null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, ENTITY_NOT_FOUND);
            }
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if(vehicle!=null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not found!");
            }

            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renter, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
}
