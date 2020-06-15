package com.leyou.service;

import com.leyou.dao.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> findAll(Category category) {
        return categoryMapper.select(category);
    }

    public Category findByid(Integer id) {

        return categoryMapper.findByid(6);
    }

    public void add(Category category) {
        categoryMapper.insert(category);
    }

    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    public void deleteById(Category category) {
        categoryMapper.deleteByPrimaryKey(category);
    }

    public Category findCategoryBycid(Long id) {

        return categoryMapper.selectByPrimaryKey(id);
    }

    public List<Category> findCategoryBycids(List cids) {
        List<Category> list = new ArrayList<>();

        cids.forEach(cid->{
            Category category = categoryMapper.selectByPrimaryKey(cid);
            list.add(category);
        });
        return  list;
    }
}
