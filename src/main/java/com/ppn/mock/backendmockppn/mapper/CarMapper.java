package com.ppn.mock.backendmockppn.mapper;

import com.ppn.mock.backendmockppn.dto.CarDto;
import com.ppn.mock.backendmockppn.entities.Car;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDto carToCarDto(Car car);

    Car carDtoToCar(CarDto carDto);
}
