package com.ppn.mock.backendmockppn.controller;


import com.ppn.mock.backendmockppn.dto.CarInformationDto;
import com.ppn.mock.backendmockppn.dto.ResponseDto;
import com.ppn.mock.backendmockppn.service.impl.CarInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("car-info")
public class CarInformationController {
    @Autowired
    private CarInformationService carInformationService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDto> add(@RequestBody @Valid CarInformationDto carInformationDto) {
        ResponseDto responseDto = new ResponseDto(HttpStatus.CREATED, carInformationService.addDetailCarInformation(carInformationDto));
        return ResponseEntity.ok(responseDto);
    }
}
