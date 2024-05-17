package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.LenderDTO;
import com.vehicool.vehicool.business.service.LenderService;
import com.vehicool.vehicool.persistence.entity.Lender;
import com.vehicool.vehicool.util.mappers.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.vehicool.vehicool.util.constants.Messages.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/lender")
public class LenderController {
    private final ModelMapper modelMapper;
    private final LenderService lenderService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid LenderDTO lenderDTO) {
        try {
            Lender lender = modelMapper.map(lenderDTO, Lender.class);
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
            lender = modelMapper.map(lenderDTO, Lender.class);
            lenderService.update(lender,id);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK,lender,RECORD_CREATED);
        }catch (Exception e){
            log.error(ERROR_OCCURRED, e.getMessage());
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());

        }
    }
}
