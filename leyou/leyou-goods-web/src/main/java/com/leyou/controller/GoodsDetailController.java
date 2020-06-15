package com.leyou.controller;

import com.leyou.client.*;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
public class GoodsDetailController {

    @Autowired
    SpuClient spuClient;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    BrandClient brandClient;
    @Autowired
    SpecClient specClient;
    @Autowired
    SkuClient skuClient;
    @Autowired
    TemplateEngine templateEngine;

    @RequestMapping("item/{spuId}.html")
    public  String item(@PathVariable("spuId") Long spuId,Model model){

        //查询SPu
        Spu spu = spuClient.findSpuById(spuId);

        //查询三级分类
        List<Category> categoryList = categoryClient.findCategoryBycids(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        //查询品牌
        Brand brand = brandClient.findBrandById(spu.getBrandId());

        //查询规格参数组及组内的信息
        List<SepcGroup> groupList = specClient.findBySepcGroupCid(spu.getCid3());

        //查询规格参数
        List<SepcParam> paramList = specClient.paramsBycidAndGeneric(spu.getCid3());
        //规格参数中的特殊属性
        HashMap<Long, String> paramMap = new HashMap<>();
        paramList.forEach(param->{
            paramMap.put(param.getId(),param.getName());
        });

        //查询sku
        List<Sku> skuList = skuClient.findSkuBySupId(spu.getId());

        //查询detail
        SpuDetail spudetail = spuClient.findSpuDetailBySpuId(spu.getId());

        model.addAttribute("spudetail",spudetail);
        model.addAttribute("spu",spu);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("brand",brand);
        model.addAttribute("groupList",groupList);
        model.addAttribute("paramMap",paramMap);
        model.addAttribute("skuList",skuList);

        //写入静态文件
        CreateHtml(spudetail,spu,categoryList,brand,groupList,paramMap,skuList);

        return  "item";
    }




    /**
     *
     * 通过Thymeleaf实现页面静态化
     * @param spudetail
     * @param spu
     * @param categoryList
     * @param brand
     * @param groupList
     * @param paramMap
     * @param skuList
     */
    private void CreateHtml(SpuDetail spudetail, Spu spu, List<Category> categoryList, Brand brand, List<SepcGroup> groupList, HashMap<Long, String> paramMap, List<Sku> skuList) {

        PrintWriter writer = null;

        try {
            //1、创建上下文对象
            Context context =new Context();
            //2、把数据放入上下文中
            context.setVariable("spudetail",spudetail);
            context.setVariable("spu",spu);
            context.setVariable("categoryList",categoryList);
            context.setVariable("brand",brand);
            context.setVariable("groupList",groupList);
            context.setVariable("paramMap",paramMap);
            context.setVariable("skuList",skuList);

            //3、写入文件 ，写入流
            File file = new  File("F:\\Idea\\nginx\\nginx-1.16.1\\html\\"+spu.getId()+".html");
            writer= new PrintWriter(file);
            //4、 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                //5、关流
                writer.close();
            }
        }

    }

}
