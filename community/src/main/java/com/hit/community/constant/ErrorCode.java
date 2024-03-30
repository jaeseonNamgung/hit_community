package com.hit.community.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(true, HttpStatus.OK, "Success");
    private final Boolean success;
    private final HttpStatus httpStatus;
    private final String message;
}
