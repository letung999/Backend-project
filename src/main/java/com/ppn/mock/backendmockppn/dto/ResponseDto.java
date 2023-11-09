package com.ppn.mock.backendmockppn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResponseDto<T> {
    private String timestamp;
    private String message;
    private HttpStatus status;
    private T data;
    private List<T> list;
    private String uri;

    public ResponseDto(String timestamp, String message, HttpStatus statusCode, T data, List<T> list, String uri) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = statusCode;
        this.data = data;
        this.list = list;
        this.uri = uri;
    }

    public ResponseDto(HttpStatus status, List<T> list) {
        this.status = status;
        this.list = list;
    }

    public ResponseDto(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public ResponseDto(HttpStatus status) {
        this.status = status;
    }
}
