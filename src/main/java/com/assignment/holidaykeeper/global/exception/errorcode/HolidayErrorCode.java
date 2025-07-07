package com.assignment.holidaykeeper.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum HolidayErrorCode implements ErrorCode {

    HOLIDAY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 공휴일을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
