package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.Page;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

     @Autowired
    BrandMapper brandMapper;

//    public Page<Brand> findBrand(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
//
//        PageHelper.startPage(page,rows);
//        List<Brand> brandlist= brandMapper.findBrand(key,sortBy,desc);
//        PageInfo<Brand> pageInfo  =new PageInfo<Brand>(brandlist);
//
//          return  new Page<Brand>(pageInfo.getTotal(),pageInfo.getList());
//    }

     //limit 分页
     public Page<Brand> findBrandByLimit(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

         List<Brand> brandList= brandMapper.findBrandByLimit(key,(page-1)*rows,rows,sortBy,desc);

         Long brandCount=brandMapper.findBrandcount(key,sortBy,desc);

         return new  Page<Brand>(brandCount,brandList);
     }

    public void addOrEditBrand(Brand brand, List<Long> cids) {

         //商品品牌表的保存
        brandMapper.insertSelective(brand);

        //商品分类表的保存  Lambad表达式  (res=>{}) ---> 箭头函数
         cids.forEach(cid->{
             brandMapper.addCategoryBrandById(brand.getId(),cid);
         });

    }

    public void deleteById(Long id) {

         //1、删除品牌表
         Brand brand =new Brand();
        brand.setId(id);
        brandMapper.deleteByPrimaryKey(brand);
        //2、删除关系表
        brandMapper.deleteBycategory_brand(id);
    }

    public List<Category> findCategory_brand(Long cid) {

         return brandMapper.findCategory_brand(cid);
    }

    public void updateOrEditBrand(Brand brand, List<Long> cids) {
        brandMapper.updateByPrimaryKey(brand);

        //先删除
        brandMapper.deleteBycategory_brand(brand.getId());

         //再添加
         cids.forEach(cid->{
             brandMapper.addCategoryBrandById(brand.getId(),cid);
         });

    }

    public List<Brand> findCategoryByCid(Long cid) {

         return brandMapper.findCategoryByCid(cid);
    }


    /**
     * 根据id查询品牌表
     * @param id
     * @return
     */
    public Brand findBrandById(Long id) {

         return brandMapper.selectByPrimaryKey(id);
    }
}
