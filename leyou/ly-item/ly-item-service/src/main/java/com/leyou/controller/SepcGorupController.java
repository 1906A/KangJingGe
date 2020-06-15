package com.leyou.controller;

import com.leyou.pojo.SepcGroup;
import com.leyou.service.SepcGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SepcGorupController {

    @Autowired
    private SepcGroupService sepcGroupService;

    /**
     *  根据cid查询商品组
     * @param cid
     * @return
     */
    @RequestMapping("groups/{cid}")
    public List<SepcGroup> findBySepcGroupCid(@PathVariable("cid") Long cid){

        return  sepcGroupService.findBySepcGroupCid(cid);
    }


    /**
     *  添加商品分组
     * @param sepcGroup
     */
     @RequestMapping("group")
    public  void SaveSepcgroup(@RequestBody SepcGroup sepcGroup){

         sepcGroupService.SaveSepcgroup(sepcGroup);
     }


     @RequestMapping("group/{id}")
    public  void deleteGroup(@PathVariable("id") Long id){

         sepcGroupService.deleteBySepcGroupId(id);
     }

}
