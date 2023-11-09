package com.ppn.mock.backendmockppn.service.contract;

import com.ppn.mock.backendmockppn.dto.CarDto;
import com.ppn.mock.backendmockppn.dto.ResponseDto;
import com.ppn.mock.backendmockppn.entities.Car;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ICarService {
    /**
     * This method to save a car
     *
     * @param carDto
     * @return
     */
    String saveCar(CarDto carDto) throws IOException;

    /**
     * This methods to deleted cars
     *
     * @param carDtos
     * @return
     */
    String deletedCars(List<CarDto> carDtos);

    /**
     * get all car information car
     *
     * @param pageable
     * @return
     */
    ResponseDto all(Pageable pageable);
}
