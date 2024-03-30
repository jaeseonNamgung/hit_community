package com.hit.community.dto;

import com.hit.community.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class ApiErrorResponse{

    private final Boolean success;
    private final String status;
    private final String message;
    public static ApiErrorResponse of(
            Boolean success,
            String status,
            String message
    ){
        return new ApiErrorResponse(success, status, message);
    }

    public static ApiErrorResponse of(
            ErrorCode errorCode
    ){
        return new ApiErrorResponse(
                errorCode.getSuccess(),
                errorCode.getHttpStatus().name(),
                errorCode.getMessage());
    }


}
