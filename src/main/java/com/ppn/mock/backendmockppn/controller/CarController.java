package com.ppn.mock.backendmockppn.controller;

import com.ppn.mock.backendmockppn.dto.CarDto;
import com.ppn.mock.backendmockppn.dto.ResponseDto;
import com.ppn.mock.backendmockppn.entities.Car;
import com.ppn.mock.backendmockppn.service.impl.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("car-management")
public class CarController {
    @Autowired
    private CarService carService;

    @Operation(summary = "Add a Car", description = "Return message 'successfully' when add success !'", tags = {"car"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully", content = @Content(schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "model of car is duplicate", content = @Content)})
    @PostMapping("add-car")
    ResponseEntity<ResponseDto> addCar(@RequestBody CarDto carDto) throws IOException {
        ResponseDto responseDto = new ResponseDto(HttpStatus.CREATED, carService.saveCar(carDto));
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "delete cars", description = "Return message 'successfully' when add success !'", tags = {"car"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successfully", content = @Content(schema = @Schema(implementation = Car.class))),
            @ApiResponse(responseCode = "400", description = "the car is assigning for a active user", content = @Content)})
    @DeleteMapping("delete")
    ResponseEntity<ResponseDto> addCar(@RequestBody List<CarDto> carDtoList) {
        ResponseDto responseDto = new ResponseDto(HttpStatus.OK, carService.deletedCars(carDtoList));
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    ResponseEntity<ResponseDto> all(@RequestParam Integer pageIndex, @RequestParam Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize);
        return ResponseEntity.ok(carService.all(pageRequest));
    }
}
