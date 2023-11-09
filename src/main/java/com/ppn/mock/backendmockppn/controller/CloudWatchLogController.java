package com.ppn.mock.backendmockppn.controller;

import com.ppn.mock.backendmockppn.service.impl.CloudWatchLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloudWatchLogController {

    @Autowired
    private CloudWatchLogService service;

    @PostMapping(path = "/publish/{message}")
    public ResponseEntity<String> logMessageToCloudWatch(@PathVariable("message") String message) {
        service.logMessageToCloudWatch(message);
        return new ResponseEntity<>("Message logged to cloudwatch", HttpStatus.OK);
    }
}