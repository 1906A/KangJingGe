package com.leyou.search;

import Bo.SpuBo;
import com.leyou.common.Page;
import com.leyou.search.Item.Goods;
import com.leyou.search.client.SpuClient;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LySearchApplicationTests {

     @Autowired
     SpuClient spuClient;

     @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

     @Autowired
     GoodsRepository goodsRepository;

     @Autowired
    GoodsService goodsService;

    @Test
    public void contextLoads() {
         //创建索引库
        elasticsearchTemplate.createIndex(Goods.class);
        //添加映射
        elasticsearchTemplate.putMapping(Goods.class);

        Page<SpuBo> spuBoPage = spuClient.findPageAll("", 2, 1, 200);
        spuBoPage.getItems().forEach(spuBo -> {
            System.out.println(spuBo.getId());
            try {
                Goods goods = goodsService.convent(spuBo);
                goodsRepository.save(goods);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
