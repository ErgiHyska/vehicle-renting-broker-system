package com.vehicool.vehicool.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "administrator")
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name",nullable = false)
    private String firstName;

    @Column(name="last_name",nullable = false)
    private String lastName;

    @Column(name="age",nullable = false)
    private Integer age;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name="phone_number",nullable = false)
    private String phoneNumber;

    @Column(name="status",nullable = false)
    private String status;
}
