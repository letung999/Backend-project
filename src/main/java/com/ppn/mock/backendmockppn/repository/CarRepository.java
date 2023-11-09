package com.ppn.mock.backendmockppn.repository;

import com.ppn.mock.backendmockppn.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarRepository extends JpaRepository<Car, Integer>, JpaSpecificationExecutor<Car> {
    Car findByModel(String model);
}
