package com.vehicool.vehicool.security.manager;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    //    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN").anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults()); // Use Customizer.withDefaults() for basic auth
//
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer -> configurer.requestMatchers("/lender/**").hasRole("LENDER")
                                                            .requestMatchers("/renter/**").hasRole("RENTER")
                        .requestMatchers("/admin-panel/**").hasRole("ADMINISTRATOR").anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/loginPageHere").loginProcessingUrl("/authenticateUserPageHere").permitAll());

        return http.build();

    }
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();

    }
}
