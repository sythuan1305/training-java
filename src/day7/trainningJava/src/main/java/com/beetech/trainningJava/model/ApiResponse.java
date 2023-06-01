package com.beetech.trainningJava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Class này dùng để trả về thông tin phản hồi của API
 */
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {
    public static final int CODE_SUCCESS = 200;
    int code;

    boolean success;

    String message;

    Object data;

    public ApiResponse(boolean success, Object data) {
        this.success = success;
        this.data = data;
        this.code = CODE_SUCCESS;
    }

    public ApiResponse(Object data, String message) {
        this.success = true;
        this.data = data;
        this.code = CODE_SUCCESS;
        this.message = message;
    }
}
