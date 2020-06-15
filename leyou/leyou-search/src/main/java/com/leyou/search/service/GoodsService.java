package com.leyou.search.service;

import Bo.SpuBo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.pojo.SepcParam;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.search.Item.Goods;
import com.leyou.search.client.SkuClient;
import com.leyou.search.client.SpecClient;
import com.leyou.search.client.SpuClient;
import com.leyou.search.repository.GoodsRepository;
import org.apache.commons.lang.math.NumberUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {

    @Autowired
    private SkuClient skuClient;

    @Autowired
    private SpecClient specClient;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    GoodsRepository goodsRepository;

    public static final ObjectMapper MAPPER=new ObjectMapper();

    public Goods convent(SpuBo spuBo) throws Exception {

        Goods goods = new Goods();
        //基础数据
        goods.setId(spuBo.getId());
        goods.setSubTitle(spuBo.getSubTitle());
        goods.setBrandId(spuBo.getBrandId());
        goods.setCid1(spuBo.getCid1());
        goods.setCid2(spuBo.getCid2());
        goods.setCid3(spuBo.getCid3());
        goods.setCreateTime(spuBo.getCreateTime());

        //all存放  标题 分类+品牌
      goods.setAll(spuBo.getTitle()+" "+spuBo.getCname().replace("/"," ")+" "+spuBo.getBname());

        //复杂数据
        //根据spuid查询sku
        List<Sku> skuList = skuClient.findSkuBySupId(spuBo.getId());

        List<Long> price =new ArrayList<>();
        skuList.forEach(sku->{
            price.add(sku.getPrice());
        });

        goods.setPrice(price);
        goods.setSkus(MAPPER.writeValueAsString(skuList));

        Map<String, Object> specs =new HashMap<>();

        //根据三级分类id和可搜索条件查询规格参数
        List<SepcParam> specParamList = specClient.findSepcParamAndSearching(spuBo.getCid3());

        //根据spuid查询spudetail
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuId(spuBo.getId());
        specParamList.forEach(sp ->{
            if(sp.getGeneric()){
                try {
                    Map<Long,Object> genericSpec = MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<Long, Object>>(){}) ;
                    String value = genericSpec.get(sp.getId()).toString();

                    if(sp.getNumeric()){
                        value = chooseSegment(value,sp);
                    }

                    specs.put(sp.getName(),value);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{

                Map<Long,Object> specialSpec = null;
                try {
                    specialSpec = MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, Object>>(){});
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String value = specialSpec.get(sp.getId()).toString();

                specs.put(sp.getName(),value);
            }

        });
        goods.setSpecs(specs);
        return goods;
    }

    private String chooseSegment(String value, SepcParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }


    /**
     * RabbitMq监听消息修改es数据
     * @param spuId
     */
    public void editEsData(Long spuId) throws Exception {
       //根据SpuId查询spu
        SpuBo spuBo = (SpuBo) spuClient.findSpuId(spuId);
        //转换为goods
        Goods goods = this.convent(spuBo);
        //持久化到Es中
        goodsRepository.save(goods);
    }
}
