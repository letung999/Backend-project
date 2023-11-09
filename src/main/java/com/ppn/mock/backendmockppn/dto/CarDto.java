package com.ppn.mock.backendmockppn.dto;

import com.ppn.mock.backendmockppn.entities.CarInformation;
import com.ppn.mock.backendmockppn.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarDto {
    private Integer id;
    private String make;
    private String model;
    private List<CarInformation> carInformations;
    private User user;
}
