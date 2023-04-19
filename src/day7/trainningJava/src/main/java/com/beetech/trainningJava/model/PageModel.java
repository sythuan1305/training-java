package com.beetech.trainningJava.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PageModel<T> {
    private List<T> items;
    private int pageNumber;
    private int totalPages;
}
