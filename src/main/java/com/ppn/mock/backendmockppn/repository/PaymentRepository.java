package com.ppn.mock.backendmockppn.repository;

import com.ppn.mock.backendmockppn.entities.Payment;
import com.ppn.mock.backendmockppn.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentRepository extends JpaRepository<Payment, Integer>, JpaSpecificationExecutor<User> {
}
