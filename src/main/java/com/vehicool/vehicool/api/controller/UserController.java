//package com.vehicool.vehicool.api.controller;
//
//import com.vehicool.vehicool.api.dto.LenderDTO;
//import com.vehicool.vehicool.business.service.DataPoolService;
//import com.vehicool.vehicool.business.service.LenderService;
//import com.vehicool.vehicool.business.service.RenterService;
//import com.vehicool.vehicool.persistence.entity.Lender;
//import com.vehicool.vehicool.util.mappers.ResponseMapper;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import static com.vehicool.vehicool.util.constants.Messages.*;
//import static com.vehicool.vehicool.util.constants.Messages.FAIL;
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@Validated
//@RequestMapping("/lender")
//public class UserController {
//    private final LenderService lenderService;
//    private final RenterService renterService;
//    private final ModelMapper modelMapper;
//    private final DataPoolService dataPoolService;
//    @PostMapping("/create-lender-profile")
//    public ResponseEntity<Object> create(@RequestBody @Valid LenderDTO lenderDTO) {
//        try {
//            Lender lender = modelMapper.map(lenderDTO, Lender.class);
//            lender.setStatus(dataPoolService.getDataPoolById(1l));
//            lenderService.save(lender);
//            return ResponseMapper.map(SUCCESS, HttpStatus.OK, lender, RECORD_CREATED);
//        } catch (Exception e) {
//            log.error(ERROR_OCCURRED, e.getMessage());
//            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
//
//        }
//    }
//}
