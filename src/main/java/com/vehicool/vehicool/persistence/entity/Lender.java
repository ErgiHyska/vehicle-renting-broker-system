package com.vehicool.vehicool.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "lender")
public class Lender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="age")
    private Integer age;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="status")
    private String status;

    @OneToMany(mappedBy ="lender")
    private List<Contract> contractSigned;

    @OneToMany(mappedBy ="lender")
    private List<RenterReview> reviewsGiven;

    @OneToMany(mappedBy ="lender")
    private List<LenderReview> reviewsRecieved;

    @OneToMany(mappedBy ="lender")
    private List<ConfidentialFile> confidentialFiles;
}
