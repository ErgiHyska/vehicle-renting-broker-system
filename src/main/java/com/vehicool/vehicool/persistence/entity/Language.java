package com.vehicool.vehicool.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "language",nullable = false)
    private String language;

    @Column(name = "language_short_code",nullable = false)
    private String languageShortCode;

    @OneToMany(mappedBy = "language")
    private List<TranslatedDataPool> translatedDataPoolList;

}
