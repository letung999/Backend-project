package com.ppn.mock.backendmockppn.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "Payment")
public class Payment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment-id")
    private Integer id;

    @Column(name = "paymentMethod", length = 255)
    private String paymentMethod;

    @Column(name = "amount", length = 255)
    private BigDecimal amount;

    @Column(name = "timeOfPayment")
    private LocalTime timeOfPayment;

    @Column(name = "dateOfPayment")
    private LocalDate dateOfPayment;

    @ManyToOne
    @JoinColumn(name = "user-id", nullable = false)
    private User user;
}
