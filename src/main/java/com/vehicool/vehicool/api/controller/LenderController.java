package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.LenderDTO;
import com.vehicool.vehicool.api.dto.StatusDTO;
import com.vehicool.vehicool.api.dto.VehicleCommercialDTO;
import com.vehicool.vehicool.business.service.*;
import com.vehicool.vehicool.persistence.entity.*;
import com.vehicool.vehicool.util.mappers.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid LenderDTO lenderDTO) {
        try {
            Lender lender = modelMapper.map(lenderDTO, Lender.class);
            lender.setStatus(dataPoolService.getDataPoolById(1l));
            lenderService.save(lender);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK,lender,RECORD_CREATED);
        }catch (Exception e){
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> update(@RequestBody @Valid LenderDTO lenderDTO,@PathVariable Long id) {
        try {
            Lender lender =lenderService.getLenderById(id);
            if(lender==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            lender = modelMapper.map(lenderDTO, Lender.class);
            lenderService.update(lender,id);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK,lender,RECORD_CREATED);
        }catch (Exception e){
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @PostMapping("{id}")
    public ResponseEntity<Object> get(@PathVariable Long id)  {
        try {
            Lender lender = lenderService.getLenderById(id);
            if(lender==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, lender, RECORD_CREATED);
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            Lender lender = lenderService.getLenderById(id);
            if(lender==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            lenderService.delete(id);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, null, "Lender deleted successfuly !");
        } catch (Exception e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
    @GetMapping("/{id}/vehicles")
    @Transactional
    public ResponseEntity<Object> listVehicles(@PathVariable Long id) {
        try {
            Lender lender = lenderService.getLenderById(id);
            if(lender==null){
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
    @GetMapping("/{id}/vehicles/{vehicleId}")
    @Transactional
    public ResponseEntity<Object> changeVehicleCommercialDetails(@RequestBody VehicleCommercialDTO vehicleCommercialDTO, @PathVariable Long id, @PathVariable Long vehicleId) {
        try {
            Lender lender = lenderService.getLenderById(id);
            if(lender==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            if(vehicle==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Vehicle not found !");
            }
            if(vehicle.getVehicleCommerce().equals(null)){
                VehicleCommerce vehicleCommerce = modelMapper.map(vehicleCommercialDTO,VehicleCommerce.class);
                vehicleCommerce.setVehicle(vehicle);
                vehicleCommerceService.save(vehicleCommerce);
            }else{
                VehicleCommerce vehicleCommerce = vehicle.getVehicleCommerce();
                vehicleCommerce.setVehicle(vehicle);
                vehicleCommerce.setPricePerDay(vehicleCommerce.getPricePerDay());
                vehicleCommerce.setDateAvailable(vehicleCommercialDTO.getDateAvailable());
                vehicleCommerce.setMaxDateAvailable(vehicleCommercialDTO.getMaxDateAvailable());
                vehicleCommerceService.update(vehicleCommerce,vehicleCommerce.getId());
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
    @GetMapping("/{id}/active-contract-requests")
    @Transactional
    public ResponseEntity<Object> listContractRequests(@PathVariable Long id) {
        try {
            Lender lender = lenderService.getLenderById(id);
            if(lender==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            List<Contract> contracts = lenderService.contractRequests(id,17l);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contracts, RECORDS_RECEIVED);
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }
    @PostMapping("/{id}/active-contract-requests/{contractId}/proceed")
    @Transactional
    public ResponseEntity<Object> proceedContractRequests(@PathVariable Long contractId,@RequestBody StatusDTO statusDTO) {
        try {
            Lender lender = lenderService.getLenderById(contractId);
            if(lender==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Lender not found !");
            }
            Contract contract = contractService.getContractById(contractId);
            if(contract==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Contract request not found !");
            }
            DataPool status=dataPoolService.getDataPoolById(statusDTO.getStatusId());
            if(status==null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "Status not found !");
            }
            contract.setContractualStatus(status);
            contractService.update(contract,contractId);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, contract, "Contract status updated !");
        } catch (PropertyReferenceException e) {
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        } catch (Exception ex) {
            log.error(ERROR_OCCURRED, ex.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, SERVER_ERROR);
        }
    }
}
