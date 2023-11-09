package com.ppn.mock.backendmockppn.service.impl;

import com.ppn.mock.backendmockppn.constant.MessageStatus;
import com.ppn.mock.backendmockppn.dto.CarInformationDto;
import com.ppn.mock.backendmockppn.exception.ResourceNotFoundException;
import com.ppn.mock.backendmockppn.mapper.CarInformationMapper;
import com.ppn.mock.backendmockppn.repository.CarInformationRepository;
import com.ppn.mock.backendmockppn.repository.CarRepository;
import com.ppn.mock.backendmockppn.service.contract.ICarInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarInformationService implements ICarInformationService {

    @Autowired
    private CarInformationRepository carInformationRepository;

    @Autowired
    private CarRepository carRepository;

    private CarInformationMapper carInformationMapper = CarInformationMapper.INSTANCE;

    @Override
    public String addDetailCarInformation(CarInformationDto carInformationDtos) {
        // Log the car information
        log.info("inside signUp {}", carInformationDtos.getCar());

        // Get the car from the database
        var carInDb = carRepository.findById(carInformationDtos.getCar().getId());

        // Throw an exception if the car is not found
        if (!carInDb.isPresent()) {
            throw new ResourceNotFoundException("car ", String.valueOf(carInformationDtos.getCar().getId()));
        }
        //mapper data
        var dataSave = carInformationMapper.carInformationDtoToCarInformation(carInformationDtos);

        //save data
        carInformationRepository.save(dataSave);
        return MessageStatus.INF_MSG_SUCCESSFULLY;
    }
}
