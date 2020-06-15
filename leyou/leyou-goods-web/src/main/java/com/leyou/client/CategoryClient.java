package com.leyou.client;

import ClientService.CategoryService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface CategoryClient extends CategoryService {

}
