package com.leyou.controller;

import com.leyou.common.Page;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.midi.Soundbank;
import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

     @Autowired
    BrandService brandService;

    @RequestMapping("page")
    public  Object findBrandByPage(@RequestParam("key") String key,
                                   @RequestParam("page") Integer page,
                                   @RequestParam("rows") Integer rows,
                                   @RequestParam("sortBy") String sortBy,
                                   @RequestParam("desc") Boolean desc){
        System.out.println(key+"=="+page+"=="+rows+"=="+sortBy+"=="+desc);

//        Page<Brand> brandList=brandService.findBrand(key,page,rows,sortBy,desc);

       Page<Brand> brandList= brandService.findBrandByLimit(key,page,rows,sortBy,desc);

        return  brandList;
    }

    //添加
    @RequestMapping("addOrEditBrand")
    public  void addOrEditBrand(Brand brand,
                                @RequestParam("cids") List<Long> cids){

         if(brand.getId()!=null){
             brandService.updateOrEditBrand(brand,cids);
         }else{
             brandService.addOrEditBrand(brand,cids);
         }

    }

    //删除
    @RequestMapping("deleteById/{id}")
    public  void deleteById(@PathVariable("id") Long id){

        brandService.deleteById(id);
    }

     //回显数据
     @RequestMapping("bid/{id}")
     public  List<Category>  findCategory_brand(@PathVariable("id") Long  cid){

          return brandService.findCategory_brand(cid);
     }


    /**
     * 根据分类id查询品牌
     * @param cid
     * @return
     */
     @RequestMapping("cid/{cid}")
    public  List<Brand> findCategoryByCid(@PathVariable("cid") Long cid){

         return brandService.findCategoryByCid(cid);
     }


    /**
     * 根据id查询品牌表
     * @param id
     * @return
     */
     @RequestMapping("findBrandById")
    public Brand findBrandById(@RequestParam("id") Long id){

         return  brandService.findBrandById(id);
     }

}
