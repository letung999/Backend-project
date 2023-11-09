package com.ppn.mock.backendmockppn.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@PropertySource("classpath:application.properties")
public class PropertiesVariable {
    @Value("${aws.log.group.name}")
    private String logGroupName;

    @Value("${aws.log.stream.name}")
    private String logStreamName;

    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

}
