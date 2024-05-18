package com.vehicool.vehicool.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
@Entity
@Table(name = "FILE_DATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "type",nullable = false)
    private String type;
    @Column(name = "file_path",nullable = false)
    private String filePath;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lender_id")
    private Lender lender;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
