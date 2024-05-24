package com.vehicool.vehicool.api.controller;

import com.vehicool.vehicool.api.dto.StatusDTO;
import com.vehicool.vehicool.business.querydsl.VehicleFilter;
import com.vehicool.vehicool.business.service.*;
import com.vehicool.vehicool.persistence.entity.DataPool;
import com.vehicool.vehicool.persistence.entity.Lender;
import com.vehicool.vehicool.persistence.entity.Renter;
import com.vehicool.vehicool.persistence.entity.Vehicle;
import com.vehicool.vehicool.security.user.Role;
import com.vehicool.vehicool.security.user.User;
import com.vehicool.vehicool.security.user.UserService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.vehicool.vehicool.util.constants.Messages.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin-panel")
public class AdminController {
    private final ModelMapper modelMapper;
    private final RenterService renterService;
    private final LenderService lenderService;
    private final DataPoolService dataPoolService;
    private final AdministratorService adminService;
    private final VehicleService vehicleService;
    private final UserService userService;

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

    @GetMapping("/list-all-users/")
    @Transactional
    public ResponseEntity<Object> listUsers(@PathVariable String username) {
        try {
            List<User> users = userService.getAllUsers();

            return ResponseMapper.map(SUCCESS, HttpStatus.OK, users, RECORDS_RECEIVED);
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @GetMapping("/list-all-users/{username}/user-data")
    @Transactional
    public ResponseEntity<Object> getUserData(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, user, RECORDS_RECEIVED);
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }

    @GetMapping("/list-all-users/{username}/confidential-files")
    @Transactional
    public ResponseEntity<Object> getUserConfidentialFiles(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            if(user == null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "USER NOT FOUND!");
            }
            List<byte[]> confidentialFiles = userService.getUserConfidentialFiles(username);
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

    @GetMapping("/list-all-users/{username}/user-status-management")
    @Transactional
    public ResponseEntity<Object> ManageUserStatuses(@PathVariable String username, @RequestBody StatusDTO StatusDTO) {
        try {
            User user = userService.getUserByUsername(username);
            if(user == null){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "USER NOT FOUND!");
            }
            DataPool status = dataPoolService.getDataPoolById(StatusDTO.getStatusId());
            if(status == null || !status.getEnumName().matches("UserStatus")){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, ERROR_OCCURRED);
            }
            Set<Role> roles = new HashSet<>();
            if(status.getEnumLabel().matches("BannedUser")){
                roles.add(Role.BANNED_USER);
                user.setRoles(roles);
                DataPool Lenderstatus = dataPoolService.findByEnumLabel("BannedLender");
                Lender lenderProfile = user.getLenderProfile();
                lenderProfile.setStatus(Lenderstatus);
                lenderService.save(lenderProfile);
                DataPool renterStatus = dataPoolService.findByEnumLabel("BannedRenter");
                Renter renterProfile= user.getRenterProfile();
                renterProfile.setStatus(renterStatus);
                renterService.save(renterProfile);
                adminService.saverUser(user,username);
                return ResponseMapper.map(SUCCESS, HttpStatus.OK, user, RECORD_UPDATED);
            }
            if(status.getEnumLabel().matches("VerifiedUser")){
                roles.add(Role.USER);
                user.setRoles(roles);
                if(user.getLenderProfile()!=null) {
                    DataPool Lenderstatus = dataPoolService.findByEnumLabel("unconfirmedLender");
                    Lender lenderProfile = user.getLenderProfile();
                    lenderProfile.setStatus(Lenderstatus);
                    lenderService.save(lenderProfile);
                }
                if(user.getRenterProfile()!=null) {
                    DataPool renterStatus = dataPoolService.findByEnumLabel("unconfirmedRenter");
                    Renter renterProfile = user.getRenterProfile();
                    renterProfile.setStatus(renterStatus);
                    renterService.save(renterProfile);
                }
                adminService.saverUser(user,username);
                return ResponseMapper.map(SUCCESS, HttpStatus.OK, user, RECORD_UPDATED);
            }
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, null, RECORDS_RECEIVED);
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, e.getMessage());
        }
    }
    //---------------------------------------- ADMI STATUS ----------------------------------------------------------------------------


}

