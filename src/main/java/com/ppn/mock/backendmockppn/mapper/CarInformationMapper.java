package com.ppn.mock.backendmockppn.mapper;

import com.ppn.mock.backendmockppn.dto.CarInformationDto;
import com.ppn.mock.backendmockppn.entities.CarInformation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CarInformationMapper {
    CarInformationMapper INSTANCE = Mappers.getMapper(CarInformationMapper.class);

    CarInformation carInformationDtoToCarInformation(CarInformationDto carInformationDto);

    CarInformationDto carInformationToCarInformationDto(CarInformation carInformation);
}
