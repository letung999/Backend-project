package com.ppn.mock.backendmockppn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ppn.mock.backendmockppn.common.model.user.request.SearchUserRequest;
import com.ppn.mock.backendmockppn.common.model.user.response.SearchUserResponse;
import com.ppn.mock.backendmockppn.config.JwtTokenProvider;
import com.ppn.mock.backendmockppn.dto.CacheData;
import com.ppn.mock.backendmockppn.dto.ResponseDto;
import com.ppn.mock.backendmockppn.dto.UserDto;
import com.ppn.mock.backendmockppn.dto.payload.LoginRequest;
import com.ppn.mock.backendmockppn.entities.CustomUserDetails;
import com.ppn.mock.backendmockppn.entities.User;
import com.ppn.mock.backendmockppn.repository.CacheDataRepository;
import com.ppn.mock.backendmockppn.service.impl.CloudWatchLogService;
import com.ppn.mock.backendmockppn.service.impl.UserService;
import com.ppn.mock.backendmockppn.utils.BuildCacheKey;
import com.ppn.mock.backendmockppn.utils.CloudWatchAppender;
import com.ppn.mock.backendmockppn.utils.GetAllKeyRedis;
import com.ppn.mock.backendmockppn.utils.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.json.JSONObject;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.ppn.mock.backendmockppn.constant.MessageStatus.ERR_MSG_SOME_THING_WENT_WRONG;
import static com.ppn.mock.backendmockppn.constant.MessageStatus.ERR_MSG_UNAUTHENTICATED_ACCESS;
import static com.ppn.mock.backendmockppn.constant.PagingConstant.PAGE_DEFAULT;
import static com.ppn.mock.backendmockppn.constant.PagingConstant.SIZE_DEFAULT;

@Slf4j
@RestController
@RequestMapping("user-management")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CloudWatchAppender cloudWatchAppender;

    @Autowired
    private CloudWatchLogService cloudWatchLogService;

    @Autowired
    private CacheDataRepository cacheDataRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = new User();
        user.setEmail(loginRequest.getEmail());
        user.setPassword(loginRequest.getPassword());
        User userCheckLogin = userService.checkLogin(user);
        try {
            if (userCheckLogin != null) {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
                String jwt = jwtTokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", jwt);
                map.put("user", userCheckLogin);
                ResponseDto<Map<String, Object>> responseDto = new ResponseDto<>(HttpStatus.OK, map);
                return ResponseEntity.ok(responseDto);
            } else {
                return ResponseEntity.ok(new ResponseDto(HttpStatus.BAD_REQUEST, ERR_MSG_UNAUTHENTICATED_ACCESS));
            }
        } catch (Exception ex) {
            return ResponseEntity.ok(new ResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR, ERR_MSG_SOME_THING_WENT_WRONG));
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signUp(@RequestBody @Valid UserDto userDto) throws IOException {
        ResponseDto responseDto = new ResponseDto(HttpStatus.CREATED, userService.signUp(userDto));
        //cloudWatchLogService.logMessageToCloudWatch("register users successfully!");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto> getAll(@RequestParam(defaultValue = PAGE_DEFAULT) @Valid Integer pageIndex, @RequestParam(defaultValue = SIZE_DEFAULT) @Valid Integer pageSize) throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize);
        ObjectMapper objectMapper = new ObjectMapper();
        String cacheKey = BuildCacheKey.buildCacheKeyAllUsers("allUsers", pageIndex, pageSize);
        Optional<CacheData> optionalCacheData = cacheDataRepository.findById(cacheKey);
        //cache hit
        if (optionalCacheData.isPresent()) {
            String userAsString = optionalCacheData.get().getValue();
            TypeReference<Map<String, Object>> mapType = new TypeReference<>() {
            };
            Map<String, Object> objectMap = objectMapper.readValue(userAsString, mapType);
            ResponseDto responseDto = new ResponseDto(HttpStatus.OK, objectMap);
            return ResponseEntity.ok(responseDto);
        }
        //cache miss
        var resultData = userService.all(pageRequest);
        var userAsString = objectMapper.writeValueAsString(resultData.getData());
        var cacheData = new CacheData(cacheKey, userAsString);
        //save cache
        cacheDataRepository.save(cacheData);

        cloudWatchAppender.flushEvents();
        var result = userService.all(pageRequest);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find-email/{email}")
    public ResponseEntity<ResponseDto> findByEmail(@PathVariable @Valid String email) {
        ResponseDto responseDto = new ResponseDto(HttpStatus.OK, userService.findByEmail(email));
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/inactive")
    public ResponseEntity<ResponseDto> inactive(@RequestBody @Valid List<Integer> ids) {
        ResponseDto responseDto = new ResponseDto(HttpStatus.OK, userService.inactive(ids));
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> update(@RequestBody UserDto userDto) {
        ResponseDto responseDto = new ResponseDto(HttpStatus.OK, userService.updateUser(userDto));

        //invalidate cache
        List<CacheData> cacheData = (List<CacheData>) cacheDataRepository.findAll();
        var keysList = cacheData.stream()
                .map(CacheData::getKey)
                .filter(x -> x.substring(0, 8).equalsIgnoreCase("allUsers"))
                .collect(Collectors.toList());
        cacheDataRepository.deleteAllById(keysList);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/search")
    public ResponseEntity<ResponseDto> search(@RequestBody @Valid SearchUserRequest request) throws JsonProcessingException {
        PageRequest pageRequest = PageRequest.of(request.getPageIndex() - 1, request.getPageSize());
        ObjectMapper objectMapper = new ObjectMapper();

        String cacheKey = request.buildCacheKey("searchUsers");
        Optional<CacheData> optionalCacheData = cacheDataRepository.findById(cacheKey);

        //cache hit
        if (optionalCacheData.isPresent()) {
            String userAsString = optionalCacheData.get().getValue();
            TypeReference<List<UserDto>> mapType = new TypeReference<List<UserDto>>() {
            };
            List<UserDto> userDtoList = objectMapper.readValue(userAsString, mapType);

            var responseData = new SearchUserResponse();
            responseData.setUserDtoList(userDtoList);
            responseData.setNumOfItems(Long.valueOf(request.getPageSize()));
            responseData.setNumOfPage((int) Math.ceil(userDtoList.size() / request.getPageSize()));
            ResponseDto responseDto = new ResponseDto(HttpStatus.OK, responseData);

            return ResponseEntity.ok(responseDto);
        }

        //cache miss
        var resultData = userService.search(request, pageRequest);
        var usersResult = resultData.getUserDtoList();
        var userAsString = objectMapper.writeValueAsString(usersResult);
        var cacheData = new CacheData(cacheKey, userAsString);

        //save cache
        cacheDataRepository.save(cacheData);

        ResponseDto responseDto = new ResponseDto(HttpStatus.OK, resultData);
        //cloudWatchLogService.logMessageToCloudWatch("search user successfully!");
        return ResponseEntity.ok(responseDto);
    }
}
