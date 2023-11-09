package com.ppn.mock.backendmockppn.dto;

import com.ppn.mock.backendmockppn.entities.Payment;
import com.ppn.mock.backendmockppn.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String age;
    private String phoneNumber;
    private String password;
    private String status;
    List<CarDto> cars;
    List<Payment> payments;
    Set<Role> roles;
}
