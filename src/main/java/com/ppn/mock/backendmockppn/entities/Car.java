package com.ppn.mock.backendmockppn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Car extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car-id")
    private Integer id;

    @Column(name = "make", length = 255, nullable = false)
    private String make;

    @Column(name = "model", length = 255, unique = true)
    private String model;

    @JsonIgnore
    @OneToMany(mappedBy = "car")
    private List<CarInformation> carInformations;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user-id", nullable = false)
    private User user;
}
