package com.leyou.search.client;

import ClientService.BrandService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "item-service")
public interface BrandClient extends BrandService {
}
