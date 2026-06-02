package com.appreciate.educationweb.student.controller.dto;

public class CommonResponseDto<T> {

    private String message;
    private String systemMessage;
    private Integer code;
    private T data;

    public CommonResponseDto(String message, Integer code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static<T> CommonResponseDto<T> success(T data) {
        return new CommonResponseDto<>(null, 0, data);
    }

    public static<T> CommonResponseDto<T> success(String message, T data) {
        return new CommonResponseDto<>(message, 0, data);
    }

    public static<T> CommonResponseDto<T> error(String message) {
        return new CommonResponseDto<>(message, 0, null);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(String systemMessage) {
        this.systemMessage = systemMessage;
    }
}
