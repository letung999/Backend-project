package com.ppn.mock.backendmockppn.common.model.user.response;

import com.ppn.mock.backendmockppn.dto.UserDto;
import lombok.Data;

import java.util.List;

@Data
public class SearchUserResponse {
    private List<UserDto> userDtoList;
    private Long numOfItems;
    private Integer numOfPage;
}
