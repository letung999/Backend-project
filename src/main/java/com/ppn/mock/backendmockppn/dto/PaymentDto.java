package com.ppn.mock.backendmockppn.dto;

import com.ppn.mock.backendmockppn.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class PaymentDto {
    private Integer id;
    private String paymentMethod;
    private BigDecimal amount;
    private LocalTime timeOfPayment;
    private LocalDate dateOfPayment;
    private User user;
}
