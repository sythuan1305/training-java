package com.beetech.trainningJava.enums;

/**
 * Enum này dùng để lưu các điều kiện của discount
 * <br>
 * Ví dụ: discount có điều kiện là tổng số tiền đơn hàng phải lớn hơn 100.000
 */
public enum ConditionType {
    TOTAL_AMOUNT,
    TOTAL_QUANTITY,
}
