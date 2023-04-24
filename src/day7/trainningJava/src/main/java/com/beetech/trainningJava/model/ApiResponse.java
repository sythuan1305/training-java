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
    int code;

    boolean success;

    String message;

    Object data;

    public ApiResponse(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }
}
