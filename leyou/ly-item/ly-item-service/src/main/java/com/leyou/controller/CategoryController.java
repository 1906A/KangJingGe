package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("list")
    public List<Category> findAll(@RequestParam("pid") long pid){
             Category category =new Category();
             category.setParentId(pid);
        return categoryService.findAll(category);
    }

    @RequestMapping("findByid")
    public Category findByid(Integer id){

        return categoryService.findByid(id);
    }

    @RequestMapping("add")
    public  String add(@RequestBody Category category){
         String ss="succ";
         try {
             categoryService.add(category);
         }catch (Exception nosucc){
             ss="nosucc";
         }
           return  ss;
    }

    @RequestMapping("update")
    public  String update(@RequestBody Category category){
        String ss="succ";
        try {
            categoryService.update(category);
        }catch (Exception nosucc){
            ss="nosucc";
        }
        return  ss;
    }

    @RequestMapping("deleteById")
    public  String deleteById(@RequestBody Category category){
        String ss="succ";
        try {
            categoryService.deleteById(category);
        }catch (Exception nosucc){
            ss="nosucc";
        }
        return  ss;
    }

     @RequestMapping("findCategoryBycid")
    public  Category findCategoryBycid(@RequestParam("id") Long id){

         return  categoryService.findCategoryBycid(id);
     }


    @RequestMapping("findCategoryBycids")
    public  List<Category> findCategoryBycids(@RequestBody List<Long> cids){

        return  categoryService.findCategoryBycids(cids);
    }
}
