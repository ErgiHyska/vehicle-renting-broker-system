package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.*;
import com.vehicool.vehicool.business.service.*;
import com.vehicool.vehicool.persistence.entity.*;
import com.vehicool.vehicool.util.mappers.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.List;

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
    private final ContractService contractService;
    private final LenderReviewService lenderReviewService;
    private final VehicleReviewService vehicleReviewService;
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

    @GetMapping("/{renterId}")
    public ResponseEntity<Object> get(@PathVariable Long renterId) {
        try {
            Renter renter = renterService.getRenterById(renterId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renter, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }


    @DeleteMapping("/{renterId}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long renterId) {
        try {
            renterService.delete(renterId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, null, "Renter deleted successfuly !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @PutMapping("/{renterId}/update")
    public ResponseEntity<Object> update(@PathVariable Long renterId, @RequestBody @Valid RenterDTO renterDTO) {
        try {
            Renter renter = renterService.getRenterById(renterId);
            modelMapper.map(renterDTO, renter);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renter, "Renter updated successfuly !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @PostMapping("/{renterId}/rentApply/{vehicleId}")
    public ResponseEntity<Object> rent(@RequestBody ContractDataDTO contractDTO, @PathVariable Long renterId, @PathVariable Long vehicleId) {
        try {
            Renter renter = renterService.getRenterById(renterId);
            if (renter == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, ENTITY_NOT_FOUND);
            }
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if (vehicle == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not found!");
            }
            if (!vehicle.getStatus().getEnumLabel().matches("VerifiedVehicle")) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not verified yet!");
            }
            if (!vehicle.getAvailable()) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not available for renting !");
            }
            if (!renter.getStatus().getEnumLabel().matches("VerifiedRenter")) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Renter is not verified");
            }
            Contract contract = new Contract();
            contract.setRenter(renter);
            contract.setLender(vehicle.getLender());
            contract.setVehicle(vehicle);
            contract.setPricePerDay(vehicle.getVehicleCommerce().getPricePerDay());
            contract.setStartDate(contractDTO.getStartDate());
            contract.setEndDate(contractDTO.getStartDate());
            long daysBetween = ChronoUnit.DAYS.between((java.time.temporal.Temporal) contractDTO.getStartDate(), (Temporal) contractDTO.getStartDate());
            contract.setTotal(daysBetween * vehicle.getVehicleCommerce().getPricePerDay());
            //Ids are hard coded corresponding to database since they won't change.Id 17 is enum_name "Waiting"
            DataPool contractualStatus = dataPoolService.getDataPoolById(17l);
            contract.setContractualStatus(contractualStatus);
            contractService.save(contract);

            return ResponseMapper.map(SUCCESS, HttpStatus.OK, renter, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @GetMapping("/{renterId}/active-contract-requests/")
    public ResponseEntity<Object> getActiveContractRequests(@PathVariable Long renterId) {
        try {
            Renter renter = renterService.getRenterById(renterId);
            if (renter == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, ENTITY_NOT_FOUND);
            }
            List<Contract> contracts = renterService.contractRequests(renterId, 17l);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contracts, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @GetMapping("/{renterId}/contract-history")
    public ResponseEntity<Object> getContractsHistory(@PathVariable Long renterId) {
        try {
            Renter renter = renterService.getRenterById(renterId);
            if (renter == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, ENTITY_NOT_FOUND);
            }
            List<Contract> contracts = renter.getContractSigned();
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contracts, RECORDS_RECEIVED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @GetMapping("/{renterId}/contract-history/{contractId}")
    public ResponseEntity<Object> getContract(@PathVariable Long renterId, @PathVariable Long contractId) {
        try {
            Renter renter = renterService.getRenterById(renterId);
            if (renter == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Renter not found!");
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

    @GetMapping("/{renterId}/active-contract-requests/{contractId}/cancel-contract")
    public ResponseEntity<Object> getActiveContractRequest(@PathVariable Long renterId, @PathVariable Long contractId) {
        try {
            Renter renter = renterService.getRenterById(renterId);
            if (renter == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Renter not found!");
            }
            Contract contract = contractService.getContractById(contractId);
            if (contract == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Contract not found!");
            }
            contract.setContractualStatus(dataPoolService.getDataPoolById(18l));
            contractService.update(contract, contractId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contract, "Contract cancelled successfully!");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
    @GetMapping("/{renterId}/active-contract-requests/{contractId}")
    public ResponseEntity<Object> getContractRequest(@PathVariable Long renterId, @PathVariable Long contractId) {
        try {
            Renter renter = renterService.getRenterById(renterId);
            if (renter == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Renter not found!");
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
    @PostMapping("/{renterId}/contract-history/{contractId}/review-lender")
    public ResponseEntity<Object> reviewRenter(@PathVariable Long renterId, @PathVariable Long contractId, @RequestBody LenderReviewDTO lenderReviewDTO)  {
        try {
            Renter renter = renterService.getRenterById(renterId);
            if(renter==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Renter not found!");
            }
            Contract contract = contractService.getContractById(contractId);
            if(contract==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Contract not found!");
            }
            Lender lender = contract.getLender();
            LenderReview lenderReview = modelMapper.map(lenderReviewDTO,LenderReview.class);
            lenderReview.setRenter(renter);
            lenderReview.setLender(lender);
            lenderReviewService.save(lenderReview);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, lenderReview, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage());

        }
    }
    @PostMapping("/{renterId}/contract-history/{contractId}/review-vehicle")
    public ResponseEntity<Object> reviewRenter(@PathVariable Long renterId, @PathVariable Long contractId, @RequestBody VehicleReviewDTO vehicleReviewDTO)  {
        try {
            Renter renter = renterService.getRenterById(renterId);
            if(renter==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Renter not found!");
            }
            Contract contract = contractService.getContractById(contractId);
            if(contract==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Contract not found!");
            }
            Vehicle vehicle = contract.getVehicle();
            VehicleReview vehicleReview = modelMapper.map(vehicleReviewDTO,VehicleReview.class);
            vehicleReview.setRenter(renter);
            vehicleReview.setVehicleReviewed(vehicle);
            vehicleReviewService.save(vehicleReview);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, vehicleReview, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, e.getMessage());

        }
    }
    @PostMapping(value = "/{renterId}/upload-confidential-data",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadImage(@PathVariable Long renterId,@RequestParam("image")List<MultipartFile> files) throws IOException {
        String uploadImage = renterService.uploadRenterConfidentialFile(files,renterId);
        return ResponseMapper.map(SUCCESS, HttpStatus.OK, uploadImage, RECORDS_RECEIVED);
    }
}
