package com.leyou.client;

import ClientService.SpuService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface SpuClient extends SpuService {

}
