package com.ppn.mock.backendmockppn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "CarInformation")
public class CarInformation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car-information-id")
    private Integer id;

    @Column(name = "color", length = 20)
    private String color;

    @Column(name = "price", length = 25)
    private BigDecimal price;

    @Column(name = "type", length = 255)
    private String type;

    @Column(name = "year")
    private Integer year;

    @Column(name = "max-speed")
    private float maxSpeech;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "car-id", nullable = false)
    private Car car;
}
