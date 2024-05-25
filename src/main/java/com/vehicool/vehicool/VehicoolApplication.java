package com.vehicool.vehicool;

import com.vehicool.vehicool.security.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VehicoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicoolApplication.class, args);

    }

}
