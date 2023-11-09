package com.ppn.mock.backendmockppn.mapper;

import com.ppn.mock.backendmockppn.dto.WorkerDto;
import com.ppn.mock.backendmockppn.entities.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WorkerMapper {
    WorkerMapper INSTANCE = Mappers.getMapper(WorkerMapper.class);

    Worker workerDtoToWorker(WorkerDto workerDto);

    WorkerDto workerToWorkerDto(Worker worker);
}
