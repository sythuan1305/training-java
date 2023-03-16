package com.beetech.trainningJava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageModel<T> {
    private List<T> items;
    private int pageNumber;
    private int totalPages;
}
