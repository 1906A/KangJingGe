package com.leyou.client;

import ClientService.SpecService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface SpecClient extends SpecService {

}
