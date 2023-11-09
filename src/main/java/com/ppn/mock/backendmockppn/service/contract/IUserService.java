package com.ppn.mock.backendmockppn.service.contract;

import com.ppn.mock.backendmockppn.common.model.user.request.SearchUserRequest;
import com.ppn.mock.backendmockppn.common.model.user.response.SearchUserResponse;
import com.ppn.mock.backendmockppn.dto.ResponseDto;
import com.ppn.mock.backendmockppn.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    /**
     * this method to find information by email in system and check users have existed in system when register to avoid duplicate.
     *
     * @param email
     * @return
     */
    UserDto findByEmail(String email);

    /**
     * this method to sign up user in system.
     *
     * @param userDto
     * @return
     */
    String signUp(UserDto userDto) throws IOException;

    /**
     * this method get and paging number of user in system
     *
     * @param pageable
     * @return
     */
    ResponseDto all(Pageable pageable);

    /**
     * This method to inactive users by list ids
     *
     * @param ids
     * @return
     */
    String inactive(List<Integer> ids);

    /**
     * this method to search user by multiple condition
     *
     * @param request
     * @param pageable
     * @return
     */
    SearchUserResponse search(SearchUserRequest request, Pageable pageable);

    UserDetails loadUserById(Integer userId);

    UserDto updateUser(UserDto userDto);
}
