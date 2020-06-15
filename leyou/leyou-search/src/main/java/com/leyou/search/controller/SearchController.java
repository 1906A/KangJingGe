package com.leyou.search.controller;

import ClientService.CategoryService;
import com.leyou.common.Page;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.pojo.SepcParam;
import com.leyou.search.Item.Goods;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.SpecClient;
import com.leyou.search.pojo.PageResult;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.repository.GoodsRepository;
import com.netflix.discovery.converters.Auto;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ssh")
public class SearchController {

     @Autowired
    GoodsRepository goodsRepository;

     @Autowired
     CategoryClient categoryClient;

     @Autowired
     BrandClient brandClient;

     @Autowired
     SpecClient specClien;

    @RequestMapping("/page")
    public Page<Goods> page(@RequestBody SearchRequest searchRequest){

        System.out.println(searchRequest.getFilter());
         //es中查询
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //构造查询
        //builder.withQuery(QueryBuilders.matchQuery("all",searchRequest.getKey()).operator(Operator.AND));


        //搜索过滤时需要修改代码
        BoolQueryBuilder builder1 = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("all",searchRequest.getKey()).operator(Operator.AND));
           if(searchRequest.getFilter()!=null && searchRequest.getFilter().size()>0){
               searchRequest.getFilter().keySet().forEach(key->{
                   String filter="specs."+key+".keyword";
                   if(key.equals("分类")){
                       filter="cid3";
                   }else if(key.equals("品牌")){
                       filter="brandId";
                   }
                   builder1.filter(QueryBuilders.termQuery(filter,searchRequest.getFilter().get(key)));
               });
           }

                builder.withQuery(builder1);

        //构造分页
        builder.withPageable(PageRequest.of(searchRequest.getPage()-1,searchRequest.getSize()));

        //根据新品排序
        builder.withSort(SortBuilders.fieldSort(searchRequest.getSortBy())
                .order(searchRequest.isDescending()? SortOrder.DESC :SortOrder.ASC));


        //根据分类和品牌
        String categoryName = "categoryName";
        String BrandName = "BrandName";

        //聚合分类和品牌
        builder.addAggregation(AggregationBuilders.terms(categoryName).field("cid3"));
        builder.addAggregation(AggregationBuilders.terms(BrandName).field("brandId"));

        AggregatedPage<Goods> search =(AggregatedPage<Goods>) goodsRepository.search(builder.build());

        //构造分类信息根据id查询数据库
        LongTerms aggregation =(LongTerms)search.getAggregation(categoryName);
        List<Category> categoryList= new ArrayList<>();

        aggregation.getBuckets().forEach(bucket -> {

            Long cateGoryId = (Long) bucket.getKey();
            //根据分类id查询数据库
            Category category =  categoryClient.findCategoryBycid(cateGoryId);
            categoryList.add(category);
        });


        //构造品牌信息根据id查询数据库
        LongTerms bAggregation =(LongTerms)search.getAggregation(BrandName);
        List<Brand> BrandList= new ArrayList<>();

        bAggregation.getBuckets().forEach(bucket -> {

            Long BandId = (Long) bucket.getKey();
            //根据分类id查询数据库
            Brand brand =  brandClient.findBrandById(BandId);
            BrandList.add(brand);
        });


        //构造商品参数组根据cid3去查询specParam
        List<Map<String,Object>> paramList = new ArrayList<>();
         if(categoryList.size()==1){
             List<SepcParam> sepcParamList= specClien.findSepcParamAndSearching(categoryList.get(0).getId());
             sepcParamList.forEach(sepcParam -> {
                 String key = sepcParam.getName();
                 builder.addAggregation(AggregationBuilders.terms(key).field("specs."+key+".keyword"));
             });
             AggregatedPage<Goods> search1 =(AggregatedPage<Goods>) goodsRepository.search(builder.build());
             Map<String, Aggregation> aggregationMap = search1.getAggregations().asMap();
             aggregationMap.keySet().forEach(mKey->{
                 if(!(mKey.equals(categoryName) || mKey.equals(BrandName))){
                     StringTerms aggregation1 =(StringTerms)aggregationMap.get(mKey);
                    //封装到Map对象
                     Map<String, Object> map = new HashMap<>();
                     map.put("key",mKey);
                     List<Map<String,String>> list = new ArrayList<>();
                     //遍历桶
                     aggregation1.getBuckets().forEach(buk->{
                         Map<String, String> valueMap = new HashMap<>();
                         valueMap.put("name",buk.getKeyAsString());
                         list.add(valueMap);
                     });
                         map.put("options",list);
                         //把数据放入paramList中
                       paramList.add(map);
                 }
             });
         }

        return new PageResult(search.getTotalElements(), search.getContent(),
                search.getTotalPages(),BrandList,categoryList,paramList);
    }
}
