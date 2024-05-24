package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.*;
import com.vehicool.vehicool.business.service.*;
import com.vehicool.vehicool.persistence.entity.*;
import com.vehicool.vehicool.util.mappers.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

import static com.vehicool.vehicool.util.constants.Messages.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/lender")
public class LenderController {
    private final ModelMapper modelMapper;
    private final LenderService lenderService;
   private final DataPoolService dataPoolService;
    private final VehicleService vehicleService;
    private final VehicleCommerceService vehicleCommerceService;
    private final ContractService contractService;
    private final RenterReviewService renterReviewService;
    private final StorageService storageService;


    @PutMapping("/{lenderId}/update")
    public ResponseEntity<Object> update(@RequestBody @Valid LenderDTO lenderDTO, @PathVariable Long lenderId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            lender = modelMapper.map(lenderDTO, Lender.class);
            lenderService.update(lender, lenderId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, lender, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @GetMapping("/{lenderId}")
    public ResponseEntity<Object> get(@PathVariable Long lenderId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, lender, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @DeleteMapping("/{lenderId}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long lenderId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            lenderService.delete(lenderId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, null, "Lender deleted successfuly !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @PostMapping("/{lenderId}/lender-vehicles/{vehicleId}/set-commercial-data")
    @Transactional
    public ResponseEntity<Object> changeVehicleCommercialDetails(@PathVariable Long lenderId, @PathVariable Long vehicleId, @RequestBody VehicleCommercialDTO vehicleCommercialDTO) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not found !");
            }
            if (vehicle.getVehicleCommerce().equals(null)) {
                VehicleCommerce vehicleCommerce = modelMapper.map(vehicleCommercialDTO, VehicleCommerce.class);
                vehicleCommerce.setVehicle(vehicle);
                vehicleCommerceService.save(vehicleCommerce);
            } else {
                VehicleCommerce vehicleCommerce = vehicle.getVehicleCommerce();
                vehicleCommerce.setVehicle(vehicle);
                vehicleCommerce.setPricePerDay(vehicleCommerce.getPricePerDay());
                vehicleCommerce.setDateAvailable(vehicleCommercialDTO.getDateAvailable());
                vehicleCommerce.setMaxDateAvailable(vehicleCommercialDTO.getMaxDateAvailable());
                vehicleCommerceService.update(vehicleCommerce, vehicleCommerce.getId());
            }
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, lender.getVehicles(), RECORDS_RECEIVED);
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @GetMapping("/{lenderId}/active-contract-requests")
    @Transactional
    public ResponseEntity<Object> listContractRequests(@PathVariable Long lenderId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            List<Contract> contracts = lenderService.contractRequests(lenderId, 17l);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contracts, RECORDS_RECEIVED);
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @PostMapping("/{lenderId}/active-contract-requests/{contractId}/proceed")
    @Transactional
    public ResponseEntity<Object> proceedContractRequests(@PathVariable Long lenderId, @PathVariable Long contractId, @RequestBody StatusDTO statusDTO) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            Contract contract = contractService.getContractById(contractId);
            if (contract == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Contract request not found !");
            }
            DataPool status = dataPoolService.getDataPoolById(statusDTO.getStatusId());
            if (status == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Status not found !");
            }
            if (!lender.getStatus().getEnumLabel().matches("VerifiedLender")) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender is not verified");
            }
            contract.setContractualStatus(status);
            contractService.update(contract, contractId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contract, "Contract status updated !");
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @GetMapping("/{lenderId}/active-contract-requests/{contractId}")
    @Transactional
    public ResponseEntity<Object> viewContractRequest(@PathVariable Long lenderId, @PathVariable Long contractId, @RequestBody StatusDTO statusDTO) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            Contract contract = contractService.getContractById(contractId);
            if (contract == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Contract request not found !");
            }
            contract = contractService.getContractById(contractId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contract, RECORDS_RECEIVED);
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @GetMapping("/{lenderId}/lender-vehicles")
    @Transactional
    public ResponseEntity<Object> listVehicles(@PathVariable Long lenderId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, lender.getVehicles(), RECORDS_RECEIVED);
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }

    @PostMapping("/{lenderId}/post-a-vehicle")
    @Transactional
    public ResponseEntity<Object> createVehicle(@PathVariable Long lenderId, @RequestBody @Valid VehicleDTO vehicleDTO) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            if (!lender.getStatus().getEnumLabel().matches("VerifiedLender")) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender is not verified !");
            }
            DataPool locaton = dataPoolService.getDataPoolById(vehicleDTO.getCityId());
            if (locaton == null && locaton.getEnumName().matches("location")) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Location not found/exists !");
            }
            Vehicle vehicle = modelMapper.map(vehicleDTO, Vehicle.class);
            vehicle.setLender(lender);
            vehicle.setLocation(locaton);
            vehicle.setAvailable(false);
            vehicle.setStatus(dataPoolService.getDataPoolById(1l));
            vehicleService.save(vehicle);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, vehicle, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @DeleteMapping("/{lenderId}/lender-vehicles/{vehicleId}/delete-vehicle")
    @Transactional
    public ResponseEntity<Object> deleteVehicle(@PathVariable Long lenderId, @PathVariable Long vehicleId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle != null) {
                vehicleService.delete(vehicleId);
            }
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, vehicle, "Vehicle deleted successfuly !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());


        }
    }

    @PutMapping("/{lenderId}/lender-vehicles/{vehicleId}/update-vehicle")
    @Transactional
    public ResponseEntity<Object> updateVehicle(@PathVariable Long lenderId, @PathVariable Long vehicleId, @RequestBody @Valid VehicleDTO vehicleDTO) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not found !");
            }
            modelMapper.map(vehicleDTO, vehicle);
            vehicleService.update(vehicle, vehicleId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, vehicle, "Vehicle updated successfuly !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @GetMapping("/{lenderId}/contract-history")
    public ResponseEntity<Object> getContractsHistory(@PathVariable Long lenderId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, ENTITY_NOT_FOUND);
            }
            List<Contract> contracts = lender.getContractSigned();
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contracts, RECORDS_RECEIVED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @GetMapping("/{lenderId}/contract-history/{contractId}")
    public ResponseEntity<Object> getContract(@PathVariable Long lenderId, @PathVariable Long contractId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found!");
            }
            Contract contract = contractService.getContractById(contractId);
            if (contract == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Contract not found!");
            }
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contract, RECORDS_RECEIVED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @PostMapping("/{lenderId}/contract-history/{contractId}/review-renter")
    public ResponseEntity<Object> reviewRenter(@PathVariable Long lenderId, @PathVariable Long contractId, @RequestBody RenterReviewDTO renterReviewDTO) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found!");
            }
            Contract contract = contractService.getContractById(contractId);
            if (contract == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Contract not found!");
            }
            Renter renter = contract.getRenter();
            RenterReview renterReview = modelMapper.map(renterReviewDTO, RenterReview.class);
            renterReview.setRenter(renter);
            renterReview.setLender(lender);
            renterReviewService.save(renterReview);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renterReview, RECORDS_RECEIVED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @PostMapping(value = "/{lenderId}/lender-vehicles/{vehicleId}/upload-vehicle-images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Object> uploadVehicleImages(@RequestParam("image") List<MultipartFile> files, @PathVariable Long lenderId, @PathVariable Long vehicleId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not found !");
            }
            String result = vehicleService.uploadVehicleImages(files, vehicleId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, result, "Vehicle pictures uploaded successfuly !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());


        }
    }

    @GetMapping(value = "/{lenderId}/lender-vehicles/{vehicleId}/imagesIds")
    @Transactional
    public ResponseEntity<Object> vehicleImagesIds(@PathVariable Long lenderId, @PathVariable Long vehicleId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not found !");
            }
            List<Long> imgIds = vehicle.getImages().stream().map(img -> img.getId()).toList();
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, imgIds, "Ids retrieved!");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }
    @DeleteMapping(value = "/{lenderId}/lender-vehicles/{vehicleId}/deleteImage/{imageId}")
    @Transactional
    public ResponseEntity<Object> vehicleImagesIds(@PathVariable Long lenderId, @PathVariable Long vehicleId,@PathVariable Long imageId) {
        try {
            Lender lender = lenderService.getLenderById(lenderId);
            if (lender == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not found !");
            }
            FileData fileData = storageService.getStorageFileById(imageId);
            if (fileData != null) {
                storageService.deleteStorageFile(imageId);
            }
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, null, "Image deleted successfully !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

}
