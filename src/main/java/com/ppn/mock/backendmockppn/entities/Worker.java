package com.ppn.mock.backendmockppn.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "worker")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worker-id")
    private Integer id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "status")
    private String status;

    @Column(name = "experiences")
    private String experiences;

    @Column(name = "workerFare")
    private BigDecimal workerFare;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "worker-car",
            joinColumns = @JoinColumn(name = "worker-id", referencedColumnName = "worker-id"),
            inverseJoinColumns = @JoinColumn(name = "car-id", referencedColumnName = "car-id", table = "car"))
    private List<Car> cars;
}
