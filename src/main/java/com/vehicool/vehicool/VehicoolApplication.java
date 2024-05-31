package com.vehicool.vehicool;

import com.vehicool.vehicool.business.service.AdministratorService;
import com.vehicool.vehicool.business.service.DataPoolService;
import com.vehicool.vehicool.business.service.LenderService;
import com.vehicool.vehicool.business.service.RenterService;
import com.vehicool.vehicool.persistence.entity.Lender;
import com.vehicool.vehicool.persistence.entity.Renter;
import com.vehicool.vehicool.security.auth.AuthenticationService;
import com.vehicool.vehicool.security.auth.RegisterRequest;
import com.vehicool.vehicool.security.user.Role;
import com.vehicool.vehicool.security.user.User;
import com.vehicool.vehicool.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class VehicoolApplication {
    private final AuthenticationService authenticationService;
    private final LenderService lenderService;
    private final RenterService renterService;
    private final UserService userService;
    private final AdministratorService administratorService;
    private final JdbcTemplate jdbcTemplate;
    private final DataPoolService dataPoolService;

    public static void main(String[] args) {
        SpringApplication.run(VehicoolApplication.class, args);
    }

    @Bean
    public String createOwnerProfile() throws IOException {
        if (dataPoolService.totalElements() == 0) {
            ClassPathResource resource = new ClassPathResource("inserts.sql");
            String script = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            jdbcTemplate.execute(script);
            System.out.println("SQL script executed successfully!");
        }
        if (userService.getUserByUsername("owner") == null) {
            authenticationService.register(new RegisterRequest("owner", "vehicool", "project", "vehicool@gmail.com","0682011111", "12345678"));
            User user = userService.getUserByUsername("owner");
            Set<Role> roles = new HashSet<>();
            roles.add(Role.ADMIN);
            roles.add(Role.LENDER);
            roles.add(Role.RENTER);
            roles.add(Role.USER);
            user.setRoles(roles);
            user.setUserStatus(dataPoolService.findByEnumLabel("VerifiedUser"));
            administratorService.saverUser(user, user.getUsername());
        }
        if (userService.getUserByUsername("lender") == null) {
            authenticationService.register(new RegisterRequest("lender", "lender", "lender", "lender@lender.com","0672011111", "12345678"));
            User user = userService.getUserByUsername("lender");
            Set<Role> roles = new HashSet<>();
            roles.add(Role.LENDER);
            roles.add(Role.USER);
            user.setRoles(roles);
            user.setUserStatus(dataPoolService.findByEnumLabel("VerifiedUser"));
            Lender lender = new Lender();
            lender.setUser(user);
            lender.setStatus(dataPoolService.findByEnumLabel("VerifiedLender"));
            lenderService.save(lender);
            user.setLenderProfile(lender);
            administratorService.saverUser(user, user.getUsername());
        }
        if (userService.getUserByUsername("renter") == null) {
            authenticationService.register(new RegisterRequest("renter", "renter", "renter", "renter@renter.com","0692011111", "12345678"));
            User user = userService.getUserByUsername("renter");
            Set<Role> roles = new HashSet<>();
            roles.add(Role.RENTER);
            roles.add(Role.USER);
            user.setRoles(roles);
            user.setUserStatus(dataPoolService.findByEnumLabel("VerifiedUser"));
            Renter renter = new Renter();
            renter.setStatus(dataPoolService.findByEnumLabel("VerifiedRenter"));
            renter.setUser(user);
            renterService.save(renter);
            user.setRenterProfile(renter);
            administratorService.saverUser(user, user.getUsername());
        }
        return "ACCOUNTS CREATED";
    }

}
