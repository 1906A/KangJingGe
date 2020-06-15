package com.leyou.search.pojo;

import com.leyou.common.Page;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.search.Item.Goods;

import java.util.List;
import java.util.Map;

public class PageResult extends Page<Goods> {

     private  List<Brand> brandList;

     private  List<Category> categoryList;

     private   List<Map<String,Object>> paramList;


    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Map<String, Object>> getParamList() {
        return paramList;
    }

    public void setParamList(List<Map<String, Object>> paramList) {
        this.paramList = paramList;
    }

    public PageResult(Long total, List<Brand> brandList, List<Category> categoryList, List<Map<String, Object>> paramList) {
        super(total);
        this.brandList = brandList;
        this.categoryList = categoryList;
        this.paramList = paramList;
    }

    public PageResult(Long total, List<Goods> items, List<Brand> brandList, List<Category> categoryList, List<Map<String, Object>> paramList) {
        super(total, items);
        this.brandList = brandList;
        this.categoryList = categoryList;
        this.paramList = paramList;
    }

    public PageResult(Long total, List<Goods> items, Integer totalPages, List<Brand> brandList, List<Category> categoryList, List<Map<String, Object>> paramList) {
        super(total, items, totalPages);
        this.brandList = brandList;
        this.categoryList = categoryList;
        this.paramList = paramList;
    }
}
