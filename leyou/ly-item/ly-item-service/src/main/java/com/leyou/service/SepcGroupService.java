package com.leyou.service;

import com.leyou.dao.SepcGroupMapper;
import com.leyou.dao.SepcParamMapper;
import com.leyou.pojo.SepcGroup;
import com.leyou.pojo.SepcParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SepcGroupService {

     @Autowired
     private SepcGroupMapper sepcGroupMapper;

    @Autowired
    private SepcParamMapper sepcParamMapper;

    /**
     *  //根据id查询商品组
     * @param cid
     * @return
     */
    public List<SepcGroup> findBySepcGroupCid(Long cid) {
        SepcGroup sepcGroup = new SepcGroup();
        sepcGroup.setCid(cid);
        //根据分类id查询规格参数组及组内的参数列表
         List <SepcGroup> groupList = new ArrayList();
        groupList =sepcGroupMapper.select(sepcGroup);
        groupList.forEach(group->{
            SepcParam param =new SepcParam();
            param.setGroupId(group.getId());
            group.setParamList(sepcParamMapper.select(param));
        });
        return groupList;

    }


    /**
     *  添加商品分组
     * @param sepcGroup
     *
     */
    public void SaveSepcgroup(SepcGroup sepcGroup) {

        if(sepcGroup.getId()==null){
            sepcGroupMapper.insert(sepcGroup);
        }else {
            sepcGroupMapper.updateByPrimaryKey(sepcGroup);
        }

    }


  public void deleteBySepcGroupId(Long id) {
        sepcGroupMapper.deleteByPrimaryKey(id);
    }


}
