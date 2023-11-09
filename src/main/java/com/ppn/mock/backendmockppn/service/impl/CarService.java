package com.ppn.mock.backendmockppn.service.impl;

import com.ppn.mock.backendmockppn.constant.MessageStatus;
import com.ppn.mock.backendmockppn.dto.CarDto;
import com.ppn.mock.backendmockppn.dto.CarTest;
import com.ppn.mock.backendmockppn.dto.ResponseDto;
import com.ppn.mock.backendmockppn.entities.Car;
import com.ppn.mock.backendmockppn.entities.CarInformation;
import com.ppn.mock.backendmockppn.entities.User;
import com.ppn.mock.backendmockppn.exception.ResourceDuplicateException;
import com.ppn.mock.backendmockppn.exception.ResourceNotFoundException;
import com.ppn.mock.backendmockppn.exception.car.DeletedCarException;
import com.ppn.mock.backendmockppn.mapper.CarMapper;
import com.ppn.mock.backendmockppn.repository.CarRepository;
import com.ppn.mock.backendmockppn.repository.UserRepository;
import com.ppn.mock.backendmockppn.service.contract.ICarService;
import com.ppn.mock.backendmockppn.utils.GetMockData;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarService implements ICarService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    private CarMapper carMapper = CarMapper.INSTANCE;

    @Override
    public String saveCar(CarDto carDto) throws IOException {
//        //check user assign car has existed in database
//        var userInDb = userRepository.findById(carDto.getUser().getId());
//        if (!userInDb.isPresent()) {
//            throw new ResourceNotFoundException("user", String.valueOf(carDto.getUser().getId()));
//        }
//        //check duplicate model in database
//        if (!carDto.getModel().isEmpty() && carDto.getModel() != null) {
//            var carInDb = carRepository.findByModel(carDto.getModel());
//            if (!Objects.isNull(carInDb)) {
//                throw new ResourceDuplicateException("model", carInDb.getModel());
//            }
//        }
//        //save data in database
//        carRepository.save(carMapper.carDtoToCar(carDto));

        var cars = GetMockData.getMockData("cars.json", CarTest[].class);
        String defaultPassword = "123456";
        int index = 0;
        var resultData = cars.stream().map(x -> {
            Integer i = 0;
            Car car = new Car();
            car.setMake(x.getMake());
            car.setModel(x.getModel());

            //int random_int = (int) Math.floor(Math.random() * (500 - 2 + 1) + 2);

            int[] rand = new Random().ints(1, 500).distinct().limit(100).toArray();

            User user = new User();
            user.setId(rand[i++]);

            CarInformation carInformation = new CarInformation();
            carInformation.setColor(x.getColor());
            carInformation.setPrice(x.getPrice());
            carInformation.setYear(x.getYear());

            var carInformations = new ArrayList<CarInformation>();
            carInformations.add(carInformation);

            car.setUser(user);

            car.setCarInformations(carInformations);

            return car;
        }).collect(Collectors.toList());

        carRepository.saveAll(resultData);
        return MessageStatus.INF_MSG_SUCCESSFULLY;
//        return MessageStatus.INF_MSG_SUCCESSFULLY;
    }

    @Override
    public String deletedCars(List<CarDto> carDtos) {
        var ids = carDtos.stream().map(c -> c.getId()).collect(Collectors.toList());
        var carsInDb = carRepository.findAllById(ids);
        if (carsInDb.size() == 0) {
            throw new ResourceNotFoundException("car: ", String.valueOf(ids));
        }
        //check if the car assign for a user has active status don't allow to delete.
        var userEmail = carsInDb.stream()
                .findFirst()
                .get()
                .getUser().getEmail();
        var userInfo = userRepository.findByEmail(userEmail);
        if (!userInfo.isDeleted()) {
            throw new DeletedCarException(MessageStatus.ERR_MSG_CAR_ASSIGN_USER, new Object[]{"car", userInfo.getEmail()});
        }
        //delete car
        carsInDb.stream().forEach(x -> {
            x.setDeleted(true);
        });
        carRepository.saveAll(carsInDb);
        return MessageStatus.INF_MSG_SUCCESSFULLY;
    }

    @Override
    public ResponseDto all(Pageable pageable) {
        try {
            var carPage = carRepository.findAll(pageable);
            var cars = carPage.getContent();
            if (cars.size() == 0) {
                return new ResponseDto(HttpStatus.OK, new ArrayList<>());
            }
            //mapper data
            var resultData = cars.stream().map(c -> {
                CarDto carDto = new CarDto();
                carDto.setId(c.getId());
                carDto.setUser(c.getUser());
                carDto.setMake(c.getMake());
                carDto.setCarInformations(c.getCarInformations());
                carDto.setModel(c.getModel());
                return carDto;
            }).collect(Collectors.toList());

            //return data
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("content", resultData);
            objectMap.put("pageSize", pageable.getPageSize());
            objectMap.put("pageNumber", pageable.getPageNumber());
            objectMap.put("totalElement", carPage.getTotalElements());
            objectMap.put("pageTotal", carPage.getTotalPages());

            ResponseDto responseDto = new ResponseDto(HttpStatus.OK, objectMap);
            return responseDto;
        } catch (Exception ex) {
            log.error("can't get data repository");
        }
        return new ResponseDto(HttpStatus.OK, new ArrayList<>());
    }
}
