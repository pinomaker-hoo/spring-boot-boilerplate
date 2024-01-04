package com.mars.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination<T> {

    private int totalPages;
    private int currentPage;
    private long totalItems;
    private T data;

    @Builder
    public Pagination(int totalPages, int currentPage, long totalItems, T data) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.data = data;
    }
}
