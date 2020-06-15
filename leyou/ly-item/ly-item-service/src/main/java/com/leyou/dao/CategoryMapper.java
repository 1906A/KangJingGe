package com.leyou.dao;



import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
public interface CategoryMapper extends tk.mybatis.mapper.common.Mapper<Category> {

    Category findByid(int i);
}
