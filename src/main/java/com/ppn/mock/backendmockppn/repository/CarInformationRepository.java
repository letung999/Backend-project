package com.ppn.mock.backendmockppn.repository;

import com.ppn.mock.backendmockppn.entities.CarInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarInformationRepository extends JpaRepository<CarInformation, Integer> {
}
