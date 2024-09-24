package com.vehicool.vehicool.security.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String username;
  private String firstname;
  private String lastname;
  private Integer age;
  private String email;
  private String phoneNumber;
  private String password;
}
