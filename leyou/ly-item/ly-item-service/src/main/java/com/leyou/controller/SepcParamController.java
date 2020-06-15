package com.leyou.controller;

import com.leyou.pojo.SepcParam;
import com.leyou.service.SepcParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SepcParamController {

    @Autowired
    private SepcParamService sepcParamService;

    /**
     *  查询规格参数
     * @param gid
     * @return
     */
    @RequestMapping("params")
    public List<SepcParam> findSepcParam(@RequestParam(value = "gid",required = false) Long gid,
                                         @RequestParam(value = "cid", required = false) Long cid){

        return  sepcParamService.findSepcParam(gid,cid);
    }


    /**
     *  添加规格参数
     * @param sepcParam
     */
    @RequestMapping("param")
    public  void  saveSepcParm(@RequestBody SepcParam sepcParam){

        sepcParamService.saveSepcParm(sepcParam);
      }


    /**
     * 根据id删除规格参数
     * @param id
     */
      @RequestMapping("param/{id}")
     public  void deleteSepcParam(@PathVariable("id") Long id){
          sepcParamService.deleteSepcParam(id);
      }



    /**
     *  查询规格参数
     * @param cid
     * @return
     */
    @RequestMapping("paramsBycid")
    public List<SepcParam> findSepcParamAndSearching(@RequestParam(value = "cid") Long cid){

        return  sepcParamService.findSepcParamAndSearching(cid);
    }

    /**
     *  查询规格参数
     * @param cid
     * @return
     */
    @RequestMapping("paramsBycidAndGeneric")
    public List<SepcParam> paramsBycidAndGeneric(@RequestParam(value = "cid") Long cid){

        return  sepcParamService.paramsBycidAndGeneric(cid);
    }
}
