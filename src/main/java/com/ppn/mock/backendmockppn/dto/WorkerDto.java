package com.ppn.mock.backendmockppn.dto;

import com.ppn.mock.backendmockppn.entities.Car;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class WorkerDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String status;
    private String experiences;
    private BigDecimal workerFare;
    private List<Car> cars;
}
