package com.vehicool.vehicool.security.user;

import com.vehicool.vehicool.util.mappers.ResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLEngineResult;
import java.security.Principal;

import static com.vehicool.vehicool.util.constants.Messages.SUCCESS;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserRepository userRepository;

    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,Principal connectedUser) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    @GetMapping ("/my-details")
    public ResponseEntity<?> changePassword(Principal connectedUser) {
        User user = userRepository.findByUsername(connectedUser.getName()).orElse(null);
        return ResponseMapper.map(SUCCESS, HttpStatus.OK,user,"WORKED");
    }
}
