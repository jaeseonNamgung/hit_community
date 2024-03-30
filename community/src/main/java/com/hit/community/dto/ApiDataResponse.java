package com.hit.community.dto;

import com.hit.community.constant.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ApiDataResponse<T> extends ApiErrorResponse {

    private final T data;
    public ApiDataResponse(T data) {
        super(true, ErrorCode.OK.getHttpStatus().name(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    public static <T> ApiDataResponse<T> of(T data){
        return new ApiDataResponse<>(data);
    }


}
