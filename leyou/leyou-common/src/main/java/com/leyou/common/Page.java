package com.leyou.common;

import java.util.List;

public class Page<T> {

    private  Long total;  //总条数

    private  List<T> items; //数据

    private  Integer totalPages;   //总页数

    public Page(Long total) {
        this.total = total;
    }

    public Page(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public Page(Long total, List<T> items, Integer totalPages) {
        this.total = total;
        this.items = items;
        this.totalPages = totalPages;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
