package com.ppn.mock.backendmockppn.service.contract;

import com.ppn.mock.backendmockppn.dto.CarInformationDto;
import org.springframework.http.ResponseEntity;

public interface ICarInformationService {
    /**
     * This method to add detail information for a car
     *
     * @param carInformationDto
     * @return
     */
    String addDetailCarInformation(CarInformationDto carInformationDto);
}
