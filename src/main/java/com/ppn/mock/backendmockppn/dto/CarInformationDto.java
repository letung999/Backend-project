package com.ppn.mock.backendmockppn.dto;

import com.ppn.mock.backendmockppn.entities.Car;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CarInformationDto {
    private Integer id;
    private String color;
    private BigDecimal price;
    private String type;
    private String model;
    private Integer year;
    private float maxSpeech;
    private Car car;
}
