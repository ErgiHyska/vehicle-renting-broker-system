package com.vehicool.vehicool.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "contract")
public class DataPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enum_label",nullable = false)
    private String enumLabel;

    @Column(name = "enum_name",nullable = false)
    private String enumName;

    @OneToMany(mappedBy = "enumLabel")
    private List<TranslatedDataPool> translatedDataPoolList;
}
