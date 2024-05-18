package com.vehicool.vehicool.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "confidential_files")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfidentialFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "type",nullable = false)
    private String type;
    @Lob
    @Column(name = "imagedata",length = 1000)
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "lender_id")
    @JsonBackReference
    private Lender lender;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "renter_id")
    private Renter renter;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
