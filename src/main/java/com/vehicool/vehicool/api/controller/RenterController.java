package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.ContractDataDTO;
import com.vehicool.vehicool.api.dto.RenterDTO;
import com.vehicool.vehicool.business.service.*;
import com.vehicool.vehicool.persistence.entity.Contract;
import com.vehicool.vehicool.persistence.entity.DataPool;
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
    @PostMapping("/{id}/rentApply/{vehicleId}")
    public ResponseEntity<Object> rent(@RequestBody ContractDataDTO contractDTO, @PathVariable Long id, @PathVariable Long vehicleId){
        try {
            Renter renter = renterService.getRenterById(id);
            if(renter==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, ENTITY_NOT_FOUND);
            }
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if(vehicle==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not found!");
            }
            Contract contract = new Contract();
            contract.setRenter(renter);
            contract.setLender(vehicle.getLender());
            contract.setVehicle(vehicle);
            contract.setPricePerDay(vehicle.getVehicleCommerce().getPricePerDay());
            contract.setStartDate(contractDTO.getStartDate());
            contract.setEndDate(contractDTO.getStartDate());
            long daysBetween = ChronoUnit.DAYS.between((Temporal) contractDTO.getStartDate(), (Temporal) contractDTO.getStartDate());
            contract.setTotal(daysBetween*vehicle.getVehicleCommerce().getPricePerDay());
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
    @GetMapping("/{id}/active-contract-requests/")
    public ResponseEntity<Object> getActiveContractRequests(@PathVariable Long id)  {
        try {
            Renter renter = renterService.getRenterById(id);
            if(renter==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, ENTITY_NOT_FOUND);
            }
            List<Contract> contracts = renterService.contractRequests(id,17l);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contracts, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
    @GetMapping("/{id}/contract-history/")
    public ResponseEntity<Object> getContractsHistory(@PathVariable Long id)  {
        try {
            Renter renter = renterService.getRenterById(id);
            if(renter==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, ENTITY_NOT_FOUND);
            }
            List<Contract> contracts = renter.getContractSigned();
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contracts, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
    @GetMapping("/{id}/active-contract-requests/{contractId}/cancel-contract")
    public ResponseEntity<Object> getActiveContractRequest(@PathVariable Long id,@PathVariable Long contractId)  {
        try {
            Renter renter = renterService.getRenterById(id);
            if(renter==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Renter not found!");
            }
            Contract contract = contractService.getContractById(contractId);
            if(contract==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Contract not found!");
            }
            contract.setContractualStatus(dataPoolService.getDataPoolById(18l));
            contractService.update(contract,contractId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contract, "Contract cancelled successfully!");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
}
