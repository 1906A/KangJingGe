package com.leyou.search.listener;

import com.leyou.search.service.GoodsService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.BindingType;

@Component
public class MessageListener {


    @Autowired
    GoodsService goodsService;

    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(name = "item.edit.search.queue",durable ="true"),
            exchange =@Exchange(name="item_exchanges",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}

    ))


     public  void  editEsData(Long supId) throws Exception {
        System.out.println("开始监听修改Es数据==："+supId);

        goodsService.editEsData(supId);

        System.out.println("结束监听修改Es数据==："+supId);
    }


}
