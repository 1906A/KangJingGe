package com.leyou.dao;

import Bo.SpuBo;
import com.leyou.pojo.Spu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuMapper extends Mapper<Spu> {


    List<SpuBo> findPageAll(@Param("key") String key,
                            @Param("saleable") Integer saleable
                           );

    Spu findSpuId(@Param("supId") Long spuId);
}
