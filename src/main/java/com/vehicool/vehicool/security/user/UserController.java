package com.vehicool.vehicool.security.user;

import com.vehicool.vehicool.api.dto.AppealDTO;
import com.vehicool.vehicool.business.service.DataPoolService;
import com.vehicool.vehicool.business.service.LenderService;
import com.vehicool.vehicool.business.service.RenterService;
import com.vehicool.vehicool.business.service.StorageService;
import com.vehicool.vehicool.persistence.entity.BannedUsersAppealing;
import com.vehicool.vehicool.persistence.entity.Lender;
import com.vehicool.vehicool.persistence.entity.Renter;
import com.vehicool.vehicool.util.mappers.ResponseMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

import static com.vehicool.vehicool.util.constants.Messages.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final DataPoolService dataPoolService;
    private final UserRepository userRepository;
    private final RenterService renterService;
    private final LenderService lenderService;
    private final StorageService storageService;

    @PatchMapping("change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request, Principal connectedUser) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-details")
    public ResponseEntity<?> getDetails(Principal connectedUser) {
        try {
            User user = userRepository.findByUsername(connectedUser.getName()).orElse(null);
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, user, RECORDS_RECEIVED);
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, ERROR_OCCURRED);
        }
    }

    @PostMapping("/user-ban-appeal")
    public ResponseEntity<?> banAppeal(Principal connectedUser,@Valid AppealDTO appealDTO) {
        try {
            User user = userRepository.findByUsername(connectedUser.getName()).orElse(null);
            if (user == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "USER NOT FOUND!");
            }
            if(user.getBannedUsersAppealing()!=null && user.getBannedUsersAppealing().getStatus().getEnumLabel().matches("BanAppealingUser")){
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "YOU HAVE AN ACTIVE APPEAL!");
            }
            BannedUsersAppealing appeal = new BannedUsersAppealing();
            appeal.setUser(user);
            appeal.setDescription(appealDTO.getDescription());
            for(MultipartFile multipartFile:appealDTO.getSupportFiles()){

            }
            return ResponseMapper.map(SUCCESS, HttpStatus.OK, user, RECORDS_RECEIVED);
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, ERROR_OCCURRED);
        }
    }

    @PostMapping("/user-renter-profile-application")
    public ResponseEntity<?> renterProfileApplication(Principal connectedUser) {
        try {
            User user = userRepository.findByUsername(connectedUser.getName()).orElse(null);
            if (user == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "USER NOT FOUND!");
            }
            if (user.getRenterProfile().equals(null)) {
                Renter renter = new Renter();
                renter.setUser(user);
                renter.setStatus(dataPoolService.findByEnumLabel("unconfirmedRenter"));
                renterService.save(renter);
                user.setRenterProfile(renter);
                userRepository.save(user);
                return ResponseMapper.map(SUCCESS, HttpStatus.OK, renter, RECORDS_RECEIVED);
            }
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "User have already a renter profile!");
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, ERROR_OCCURRED);
        }
    }

    @PostMapping("/user-lender-profile-application")
    public ResponseEntity<?> lenderProfileApplication(Principal connectedUser) {
        try {
            User user = userRepository.findByUsername(connectedUser.getName()).orElse(null);
            if (user == null) {
                return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "USER NOT FOUND!");
            }
            if (user.getLenderProfile().equals(null)) {
                Lender lender = new Lender();
                lender.setUser(user);
                lender.setStatus(dataPoolService.findByEnumLabel("unconfirmedLender"));
                lenderService.save(lender);
                user.setLenderProfile(lender);
                userRepository.save(user);
                return ResponseMapper.map(SUCCESS, HttpStatus.OK, lender, RECORDS_RECEIVED);
            }
            return ResponseMapper.map(FAIL, HttpStatus.BAD_REQUEST, null, "User have already a lender profile!");
        } catch (Exception e) {
            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, ERROR_OCCURRED);
        }
    }

//    @GetMapping("/notifications")
//    public ResponseEntity<?> notifications(Principal connectedUser) {
//        try {
//            User user = userRepository.findByUsername(connectedUser.getName()).orElse(null);
//            if (user == null) {
//                return ResponseMapper.map(SUCCESS, HttpStatus.OK, user.getNotifications(), RECORDS_RECEIVED);
//            }
//            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, ERROR_OCCURRED);
//        } catch (Exception e) {
//            return ResponseMapper.map(FAIL, HttpStatus.INTERNAL_SERVER_ERROR, null, ERROR_OCCURRED);
//        }
//    }
}
