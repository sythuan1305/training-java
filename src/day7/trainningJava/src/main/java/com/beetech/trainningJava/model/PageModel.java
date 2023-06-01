package com.beetech.trainningJava.model;

import lombok.*;

import java.util.List;

/**
 * Class này dùng để trả về kết quả phân trang
 * @param <T> kiểu dữ liệu của item trong trang
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageModel<T> {
    private List<T> items;

    private int pageNumber;

    private long totalPages;
}
