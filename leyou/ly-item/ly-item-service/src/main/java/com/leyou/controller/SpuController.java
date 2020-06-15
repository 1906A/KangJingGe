package com.leyou.controller;

import com.leyou.common.Page;
import Bo.SpuBo;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spu")
public class SpuController {

     @Autowired
     private SpuService spuService;

    /**
     *商品的展示功能
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("page")
    public Page<SpuBo> findPageAll(@RequestParam("key") String key,
                                   @RequestParam("saleable") Integer saleable,
                                   @RequestParam("page") Integer page,
                                   @RequestParam("rows") Integer rows){

          Page<SpuBo> spulist=spuService.findPageAll(key,saleable,page,rows);

         return  spulist;
    }

    /**
     * 完成商品的添加功能
     * @param spuBo
     *
     */
      @PostMapping("goods")
      public  void  SaveSpuandSku(@RequestBody SpuBo spuBo){
              spuService.SaveSpuandSku(spuBo);
      }


      @RequestMapping("detail/{spuId}")
      public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId){

          return spuService.findSpuDetailBySpuId(spuId);
      }

    @PutMapping("goods")
    public  void  updateSpuandSku(@RequestBody SpuBo spuBo) {
        spuService.updateSpuandSku(spuBo);
    }

    /**
     * 根据spuId删除商品
     * @param spuId
     */
    @RequestMapping("deleteGoods/{id}")
    public void deleteGoods(@PathVariable("id") Long spuId){

        spuService.deleteGoods(spuId);
    }


    /**
     * //根据参数完成上下架功能
     * @param spuId
     * @param saleable
     */
    @RequestMapping("OnAndDown")
    public  void OnAndDown(@RequestParam("saleable") int saleable,
                           @RequestParam("spuId") Long spuId){
        spuService.OnAndDown(saleable,spuId);
    }


    /**
     *
     * 根据SpuId进行查询Spu
     * @param spuId
     * @return
     */
    @RequestMapping("findSpuByspuId")
    public Spu findSpuById(@RequestParam("spuId") Long spuId ){

        return spuService.findSpuById(spuId);
    }



    @RequestMapping("findSpuId")
    public  Spu findSpuId(@RequestParam("spuId") Long spuId){

        return spuService.findSpuId(spuId);
    }


}