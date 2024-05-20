package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.StatusDTO;
import com.vehicool.vehicool.business.querydsl.LenderFilter;
import com.vehicool.vehicool.business.querydsl.RenterFilter;
import com.vehicool.vehicool.business.querydsl.VehicleFilter;
import com.vehicool.vehicool.business.service.DataPoolService;
import com.vehicool.vehicool.business.service.LenderService;
import com.vehicool.vehicool.business.service.RenterService;
import com.vehicool.vehicool.business.service.VehicleService;
import com.vehicool.vehicool.persistence.entity.*;
import com.vehicool.vehicool.util.mappers.ResponseMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.vehicool.vehicool.util.constants.Messages.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("admin-panel/")
public class AdminController {
    private final ModelMapper modelMapper;
    private final RenterService renterService;
    private final LenderService lenderService;
    private final DataPoolService dataPoolService;
    private final VehicleService vehicleService;

    @GetMapping("/list-all-renters")
    @Transactional
    public ResponseEntity<Object> listRenters(@Valid @RequestParam Map<String, Object> renterFilterRequest,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(defaultValue = "id") String sort) {
        try {
            RenterFilter renterFilter = modelMapper.map(renterFilterRequest, RenterFilter.class);
            Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
            Page<Renter> renterPage = renterService.findAll(renterFilter, pageRequest);

            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renterPage, RECORDS_RECEIVED);
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }


    @GetMapping("/list-all-lenders")
    @Transactional
    public ResponseEntity<Object> listLenders(@Valid @RequestParam Map<String, Object> lenderFilterRequest,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              @RequestParam(defaultValue = "id") String sort) {
        try {
            LenderFilter lenderFilter = modelMapper.map(lenderFilterRequest, LenderFilter.class);
            Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
            Page<Lender> lenderPage = lenderService.findAll(lenderFilter, pageRequest);

            return ResponseMapper.map(SUCCESS, HttpStatus.OK, lenderPage, RECORDS_RECEIVED);
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }
    @GetMapping("/list-all-vehicles")
    @Transactional
    public ResponseEntity<Object> listVehicles(@Valid @RequestParam Map<String, Object> vehicleFilterRequest,
                                               @RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "10") Integer size,
                                               @RequestParam(defaultValue = "id") String sort) {
        try {
            VehicleFilter vehicleFilter = modelMapper.map(vehicleFilterRequest, VehicleFilter.class);
            Pageable pageRequest = PageRequest.of(page, size, Sort.by(sort));
            Page<Vehicle> vehiclePage = vehicleService.findAll(vehicleFilter, pageRequest);

            return ResponseMapper.map(SUCCESS, HttpStatus.OK, vehiclePage, RECORDS_RECEIVED);
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }
@GetMapping("/list-all-lenders/{id}/confidential-files")
@Transactional
public ResponseEntity<Object> getLenderConfidentialFiles(@PathVariable Long id) {
    try {
        Lender lender = lenderService.getLenderById(id);
        if (lender == null) {
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
        }

        List<byte[]> confidentialFiles = lenderService.getLenderConfidentialFiles(id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        for (int i = 0; i < confidentialFiles.size(); i++) {
            ZipEntry entry = new ZipEntry("file" + (i + 1) + ".png");
            entry.setSize(confidentialFiles.get(i).length);
            zos.putNextEntry(entry);
            zos.write(confidentialFiles.get(i));
            zos.closeEntry();
        }
        zos.close();

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=lender_files.zip").body(baos.toByteArray());
    } catch (Exception e) {
        return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
    }
}
    @GetMapping("/list-all-renters/{id}/confidential-files")
    @Transactional
    public ResponseEntity<Object> getRenterConfidentialFiles(@PathVariable Long id) {
        try {
            Renter renter = renterService.getRenterById(id);
            if (renter == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Renter not found !");
            }

            List<byte[]> confidentialFiles = renterService.getRenterConfidentialFiles(id);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

            for (int i = 0; i < confidentialFiles.size(); i++) {
                ZipEntry entry = new ZipEntry("file" + (i + 1) + ".png");
                entry.setSize(confidentialFiles.get(i).length);
                zos.putNextEntry(entry);
                zos.write(confidentialFiles.get(i));
                zos.closeEntry();
            }
            zos.close();

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=renter_files.zip").body(baos.toByteArray());
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }




    @PostMapping("/list-all-lenders/{lenderId}/set-status")
    @Transactional
    public ResponseEntity<Object> setLenderStatus(@RequestBody StatusDTO statusDTO, @PathVariable Long lenderId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            DataPool status = dataPoolService.getDataPoolById(statusDTO.getStatusId());
            if (status == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            lender.setStatus(status);
            lenderService.update(lender, lenderId);

            return ResponseMapper.map(SUCCESS, HttpStatus.OK, status.getEnumLabel(), "Lender status is set!");
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @PostMapping("/list-all-renters/{renterId}/set-status")
    @Transactional
    public ResponseEntity<Object> setRenterStatus(@RequestBody StatusDTO statusDTO, @PathVariable Long renterId) {
        try {
            Renter renter = renterService.getRenterById(renterId);
            if (renter == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Renter not found !");
            }
            DataPool status = dataPoolService.getDataPoolById(statusDTO.getStatusId());
            if (status == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Renter not found !");
            }
            renter.setStatus(status);
            renterService.update(renter, renterId);

            return ResponseMapper.map(SUCCESS, HttpStatus.OK, status.getEnumLabel(), "Renter status is set!");
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }



    @PostMapping("/list-all-vehicles/{vehicleId}/set-status")
    @Transactional
    public ResponseEntity<Object> setVehicleStatus(@RequestBody StatusDTO statusDTO, @PathVariable Long vehicleId) {
        try {
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            DataPool status = dataPoolService.getDataPoolById(statusDTO.getStatusId());
            if (status == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            vehicle.setStatus(status);
            vehicleService.update(vehicle, vehicleId);

            return ResponseMapper.map(SUCCESS, HttpStatus.OK, status.getEnumLabel(), "Lender status is set!");
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }


}

