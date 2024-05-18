package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.LenderDTO;
import com.vehicool.vehicool.business.querydsl.VehicleFilter;
import com.vehicool.vehicool.business.service.DataPoolService;
import com.vehicool.vehicool.business.service.LenderService;
import com.vehicool.vehicool.business.service.VehicleService;
import com.vehicool.vehicool.persistence.entity.Lender;
import com.vehicool.vehicool.persistence.entity.Vehicle;
import com.vehicool.vehicool.persistence.entity.VehicleCommerce;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
}
