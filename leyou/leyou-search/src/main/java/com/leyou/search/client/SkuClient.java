package com.leyou.search.client;

import ClientService.SkuService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface SkuClient extends SkuService {

}
