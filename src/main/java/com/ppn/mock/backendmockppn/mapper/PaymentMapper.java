package com.ppn.mock.backendmockppn.mapper;

import com.ppn.mock.backendmockppn.dto.PaymentDto;
import com.ppn.mock.backendmockppn.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    Payment paymentDtoToPayment(PaymentDto paymentDto);

    PaymentDto paymentToPaymentDto(Payment payment);
}
