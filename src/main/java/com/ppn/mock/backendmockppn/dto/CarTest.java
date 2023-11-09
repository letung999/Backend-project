package com.ppn.mock.backendmockppn.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class CarTest {
    private Integer id;
    private String make;
    private String color;
    private BigDecimal price;
    private Integer year;
    private String model;
}
