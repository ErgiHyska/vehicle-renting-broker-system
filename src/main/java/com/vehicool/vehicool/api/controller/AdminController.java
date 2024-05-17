package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.business.querydsl.LenderFilter;
import com.vehicool.vehicool.business.querydsl.RenterFilter;
import com.vehicool.vehicool.business.service.LenderService;
import com.vehicool.vehicool.business.service.RenterService;
import com.vehicool.vehicool.persistence.entity.Lender;
import com.vehicool.vehicool.persistence.entity.Renter;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

}

