package com.leyou.dao;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    List<Brand> findBrand(@Param("key") String key, @Param("sortBy") String sortBy, @Param("desc") Boolean desc);


    List<Brand> findBrandByLimit(@Param("key") String key,
                                 @Param("page") int page,
                                 @Param("rows") Integer rows,
                                 @Param("sortBy") String sortBy,
                                 @Param("desc") Boolean desc);

    Long findBrandcount(@Param("key") String key,
                        @Param("sortBy") String sortBy,
                        @Param("desc") Boolean desc);


     //注解式Sql语句
    @Insert("insert  into tb_category_brand values (#{cid},#{id})")
    void addCategoryBrandById(Long id, Long cid);

    //删除关系表中的对应id
    @Delete("DELETE FROM tb_category_brand WHERE  brand_id=#{id}")
    void deleteBycategory_brand(Long id);


    @Select("SELECT g.* FROM tb_category_brand t ,tb_category g WHERE t.category_id=g.id AND t.brand_id=#{cid}")
    List<Category> findCategory_brand(Long cid);

    @Select("SELECT * FROM tb_brand b,tb_category_brand c WHERE b.id=c.brand_id AND c.category_id=#{cid}")
    List<Brand> findCategoryByCid(Long cid);
}
