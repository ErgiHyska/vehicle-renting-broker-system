package com.vehicool.vehicool.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "translated_data_pool")
public class TranslatedDataPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "translated_enum_label")
    private String translatedEnumLabel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "data_pool_id")
    private DataPool enumLabel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "language_id")
    private Language language;
}
