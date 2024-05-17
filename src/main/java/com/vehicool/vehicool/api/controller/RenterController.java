package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.RenterDTO;
import com.vehicool.vehicool.business.service.RenterService;
import com.vehicool.vehicool.business.service.StorageService;
import com.vehicool.vehicool.business.service.VehicleService;
import com.vehicool.vehicool.persistence.entity.Renter;
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
    private final StorageService storageService;
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid RenterDTO renterDTO, @RequestParam("image") MultipartFile file) throws IOException {
        try {
            Renter renter = modelMapper.map(renterDTO, Renter.class);
            renterService.save(renter);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renter, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
    @PostMapping("/get/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id)  {
        try {
            Renter renter = renterService.getRenterById(id);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renter, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            renterService.delete(id);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, null, "Renter deleted successfuly !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @PutMapping("/update/{id}")
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
}
