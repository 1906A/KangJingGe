package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.Page;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import Bo.SpuBo;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SpuService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    AmqpTemplate amqpTemplate;

    public Page<SpuBo> findPageAll(String key, Integer saleable, Integer page, Integer rows) {

        PageHelper.startPage(page,rows);

        List<SpuBo> spuList=spuMapper.findPageAll(key,saleable);

        PageInfo<SpuBo> pageInfo =new PageInfo<SpuBo>(spuList);

        return  new Page<SpuBo>(pageInfo.getTotal(),pageInfo.getList());

    }

    /**
     *  完成商品添加功能
     * @param spuBo
     * 1、保存spu
     * 2、保存spudetail
     * 3、保存sku
     * 4、保存Stock
     */
    public void SaveSpuandSku(SpuBo spuBo) {

         Date nowDate=new Date();

        //1、保存spu表
         Spu spu =new Spu();
        spu.setBrandId(spuBo.getBrandId());
        spu.setCid1(spuBo.getCid1());
        spu.setCid2(spuBo.getCid2());
        spu.setCid3(spuBo.getCid3());
        spu.setSubTitle(spuBo.getSubTitle());
        spu.setTitle(spuBo.getTitle());
        spu.setSaleable(false);
        spu.setValid(true);
        spu.setCreateTime(nowDate);
        spu.setLastUpdateTime(nowDate);
         spuMapper.insert(spu);

        //2、保存spudetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);

        //3、保存sku
        List<Sku> skus = spuBo.getSkus();
        skus.forEach(sku->{
            sku.setSpuId(spu.getId());
            sku.setEnable(false);
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);

            //4、保存Stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });

        //发送Mq消息
        this.sendMsg("insert",spu.getId());

}

     //提取方法
     public  void  sendMsg(String type,Long spuId){
         //发送mq消息
         amqpTemplate.convertAndSend("item_exchanges","item."+type,spuId);
     }


    public SpuDetail findSpuDetailBySpuId(Long spuId) {

        return spuDetailMapper.selectByPrimaryKey(spuId);
    }


    /**
     *  完成商品修改功能
     * @param spuBo
     * 1、修改spu
     * 2、修改spudetail
     * 3、修改sku
     * 4、修改Stock
     */

    public void updateSpuandSku(SpuBo spuBo) {
        Date nowDate=new Date();
        Sku sku = new Sku();
        sku.setEnable(false);
        sku.setSpuId(spuBo.getId());

        List<Sku> skuList = skuMapper.select(sku);
        skuList.forEach(sku1 -> {
            stockMapper.deleteByPrimaryKey(sku1.getId());
        });
            skuMapper.delete(sku);

        List<Sku> skus1 = spuBo.getSkus();
        skus1.forEach(s->{
            s.setSpuId(spuBo.getId());
            s.setEnable(false);
            s.setCreateTime(nowDate);
            s.setLastUpdateTime(nowDate);
            skuMapper.insert(s);

            //4、保存Stock
        Stock stock = new Stock();
        stock.setSkuId(s.getId());
        stock.setStock(s.getStock());
        stockMapper.insert(stock);
    });

        //更新spu
        spuBo.setSaleable(null);
        spuBo.setValid(null);
        spuBo.setCreateTime(null);
        spuBo.setLastUpdateTime(nowDate);
        spuMapper.updateByPrimaryKeySelective(spuBo);

        //更新spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);

        //发送Mq消息
        this.sendMsg("update",spuBo.getId());
    }


    /**
     * 根据spuId删除商品
     * @param spuId
     */
    public void deleteGoods(Long spuId) {

        //删除sku表
        List<Sku> skuList = skuMapper.findSkuBySupId(spuId);
        skuList.forEach(sku -> {
            sku.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(sku);
            stockMapper.deleteByPrimaryKey(sku.getId());
        });

        spuDetailMapper.deleteByPrimaryKey(spuId);
        spuMapper.deleteByPrimaryKey(spuId);
    }

    /**
     * //根据参数完成上下架功能
     * @param spuId
     * @param saleable
     */
    public void OnAndDown( int saleable,Long spuId) {

        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setSaleable(saleable ==1 ? true:false);
        spuMapper.updateByPrimaryKeySelective(spu);

    }


    /**
     *
     * 根据SpuId进行查询Spu
     * @param spuId
     * @return
     */
    public Spu findSpuById(Long spuId) {

        return spuMapper.selectByPrimaryKey(spuId);
    }

    public Spu findSpuId(Long spuId) {

        return spuMapper.findSpuId(spuId);
    }
}
