package com.beetech.trainningJava.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Class này dùng để trả về kết quả phân trang
 * @param <T> kiểu dữ liệu của item trong trang
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class PageModel<T> {
    private List<T> items;

    private int pageNumber;

    private long totalPages;
}
