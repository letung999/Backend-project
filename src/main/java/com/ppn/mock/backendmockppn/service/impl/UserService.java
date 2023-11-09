package com.ppn.mock.backendmockppn.service.impl;


import com.ppn.mock.backendmockppn.common.model.user.request.SearchUserRequest;
import com.ppn.mock.backendmockppn.common.model.user.response.SearchUserResponse;
import com.ppn.mock.backendmockppn.constant.ApprovalStatus;
import com.ppn.mock.backendmockppn.constant.MessageStatus;
import com.ppn.mock.backendmockppn.dto.ResponseDto;
import com.ppn.mock.backendmockppn.dto.UserDto;
import com.ppn.mock.backendmockppn.entities.CustomUserDetails;
import com.ppn.mock.backendmockppn.entities.User;
import com.ppn.mock.backendmockppn.exception.ResourceDuplicateException;
import com.ppn.mock.backendmockppn.exception.ResourceNotFoundException;
import com.ppn.mock.backendmockppn.mapper.UserMapper;
import com.ppn.mock.backendmockppn.repository.UserRepository;
import com.ppn.mock.backendmockppn.service.contract.IUserService;
import com.ppn.mock.backendmockppn.utils.GetMockData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements IUserService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserMapper userMapper = UserMapper.INSTANCE;

    @Override
    public UserDto findByEmail(String email) {
        var userInDb = userRepository.findByEmail(email);
        if (Objects.isNull(userInDb)) {
            throw new ResourceNotFoundException("user", email);
        }
        var resultData = userMapper.userToUserDto(userInDb);
        return resultData;
    }

    @Override
    public String signUp(UserDto userDto) throws IOException {
        log.info("inside signUp{}", userDto);

        var userInDb = userRepository.findByEmail(userDto.getEmail());
        if (!Objects.isNull(userInDb)) {
            throw new ResourceDuplicateException("email", userDto.getEmail());
        }
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        User user = userMapper.userDtoToUser(userDto);
        user.setStatus(String.valueOf(ApprovalStatus.PENDING));
        userRepository.save(user);
        return MessageStatus.INF_MSG_SUCCESSFULLY;
//        var users = GetMockData.getMockData("persons.json", UserDto[].class);
//        String defaultPassword = "123456";
//        var resultData = users.stream().map(x -> {
//            User user = new User();
//            user.setFirstName(x.getFirstName());
//            user.setLastName(x.getLastName());
//            user.setEmail(x.getEmail());
//            user.setStatus(ApprovalStatus.PENDING.toString());
//            user.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
//            user.setAge(x.getAge());
//            user.setGender(x.getGender());
//            return user;
//        }).collect(Collectors.toList());
//
//        userRepository.saveAll(resultData);
//        return MessageStatus.INF_MSG_SUCCESSFULLY;
    }

    @Override
    public ResponseDto all(Pageable pageable) {
        var userPage = userRepository.findAll(pageable);
        var userInfoList = userPage.getContent();
        if (userInfoList.size() == 0) {
            return new ResponseDto(HttpStatus.OK, new ArrayList<>());
        }
        var resultData = userInfoList.stream().map(u -> {
            UserDto userDto = new UserDto();
            userDto.setId(u.getId());
            userDto.setFirstName(u.getFirstName());
            userDto.setLastName(u.getLastName());
            userDto.setEmail(u.getEmail());
            userDto.setPassword(u.getPassword());
            userDto.setGender(u.getGender());
            userDto.setStatus(u.getStatus());
            userDto.setPhoneNumber(u.getPhoneNumber());
            userDto.setAge(u.getAge());
            return userDto;
        }).collect(Collectors.toList());

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("content", resultData);
        objectMap.put("pageSize", pageable.getPageSize());
        objectMap.put("pageNumber", pageable.getPageNumber());
        objectMap.put("totalElement", userPage.getTotalElements());
        objectMap.put("pageTotal", userPage.getTotalPages());

        ResponseDto responseDto = new ResponseDto(HttpStatus.OK, objectMap);
        return responseDto;
    }

    @Override
    public String inactive(List<Integer> ids) {
        log.info("inside inactive", ids);

        //remove ids have duplicated.
        var idsResult = ids.stream().distinct().collect(Collectors.toList());

        var users = userRepository.findAllById(idsResult);
        if (users.size() == 0) {
            throw new ResourceNotFoundException("userIds", idsResult.toString());
        }

        //only inactive with users have active status.
        var dataFilter = users.stream().filter(x -> x.getStatus().equals(ApprovalStatus.ACTIVE.toString())).collect(Collectors.toList());
        if (dataFilter.size() == 0) {
            throw new ResourceNotFoundException("userIds: ", dataFilter.stream().map(x -> x.getId()).collect(Collectors.toList()).toString());
        }

        //update data
        dataFilter.forEach(u -> u.setStatus(ApprovalStatus.INACTIVE.toString()));
        userRepository.saveAll(dataFilter);
        return MessageStatus.INF_MSG_SUCCESSFULLY;
    }

    @Override
    public SearchUserResponse search(SearchUserRequest request, Pageable pageable) {
        var spec = buildSearchUserFilter(request);
        var userPage = userRepository.findAll(spec, pageable);
        var userData = userPage.getContent();

        //mapper data
        var userDtoList = userData.stream().map(u -> {
            var userDto = new UserDto();
            userDto.setId(u.getId());
            userDto.setFirstName(u.getFirstName());
            userDto.setLastName(u.getLastName());
            userDto.setEmail(u.getEmail());
            userDto.setPhoneNumber(u.getPhoneNumber());
            userDto.setAge(u.getAge());
            userDto.setGender(u.getGender());
            userDto.setStatus(u.getStatus());
            return userDto;
        }).collect(Collectors.toList());

        //return data
        var responseData = new SearchUserResponse();
        responseData.setUserDtoList(userDtoList);
        responseData.setNumOfItems(userPage.getTotalElements());
        responseData.setNumOfPage(userPage.getTotalPages());

        return responseData;
    }

    @Override
    public UserDetails loadUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        return new CustomUserDetails(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        var userEntity = userRepository.findById(userDto.getId());
        var userEmails = userRepository.findAll().stream()
                .map(x -> x.getEmail())
                .collect(Collectors.toList());
        if (!userEntity.isPresent()) {
            throw new ResourceNotFoundException("user", String.valueOf(userEntity.get().getId()));
        }
        if (userEmails.contains(userDto.getEmail())) {
            throw new ResourceDuplicateException("email", userDto.getEmail());
        }
        var userUpdate = userMapper.userDtoToUser(userDto);
        userRepository.save(userUpdate);
        return userDto;
    }

    public User checkLogin(User user) {
        User userCheck = null;
        if (user.getEmail() != null) {
            userCheck = userRepository.findByEmail(user.getEmail());
        }
        if (userCheck != null && bCryptPasswordEncoder.matches(user.getPassword(), userCheck.getPassword())) {
            return userCheck;
        }
        return null;
    }

    //private method

    /**
     * build search user filter
     *
     * @param request
     * @return
     */
    private Specification<User> buildSearchUserFilter(SearchUserRequest request) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> finalFilter = new ArrayList<>();
            if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("email"), request.getEmail()));
            }
            if (request.getGender() != null && !request.getGender().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("gender"), request.getGender()));
            }
            if (request.getPhoneNumber() != null && !request.getPhoneNumber().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("phoneNumber"), request.getPhoneNumber()));
            }
            if (request.getStatus() != null && !request.getStatus().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
            }
            if (request.getAge() != null && !request.getAge().isEmpty()) {
                finalFilter.add(criteriaBuilder.equal(root.get("age"), request.getAge()));
            }
            if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
                finalFilter.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + request.getFirstName().toLowerCase() + "%"));
            }
            if (request.getLastName() != null && !request.getLastName().isEmpty()) {
                finalFilter.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + request.getLastName().toLowerCase() + "%"));
            }
            if (request.isAscending() && request.getSortBy() != null && !request.getSortBy().isEmpty()) {
                query.orderBy(criteriaBuilder.asc(root.get(request.getSortBy())));
            }
            if (!request.isAscending() && request.getSortBy() != null && !request.getSortBy().isEmpty()) {
                query.orderBy(criteriaBuilder.desc(root.get(request.getSortBy())));
            }
            return criteriaBuilder.and(finalFilter.toArray(new Predicate[0]));
        });
    }
}
