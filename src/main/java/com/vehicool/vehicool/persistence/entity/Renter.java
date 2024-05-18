package com.vehicool.vehicool.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "renter")
public class Renter {
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "status")
    private DataPool status;

    @OneToMany(mappedBy ="renter")
    @JsonBackReference
    private List<Contract> contractSigned;

    @OneToMany(mappedBy ="renter")
    @JsonBackReference
    private List<RenterReview> reviewsRecieved;

    @OneToMany(mappedBy ="renter")
    @JsonBackReference
    private List<LenderReview> reviewsGiven;
}
