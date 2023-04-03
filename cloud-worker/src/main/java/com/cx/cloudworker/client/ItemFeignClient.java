package com.cx.cloudworker.client;

import com.cx.cloudworker.dto.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "sync-cloud-worker", contextId = "ItemFeignClientCtxId", configuration = FeignConfig.class)
public interface ItemFeignClient {

    @GetMapping(value = "web/item/{id}", produces = "application/json")
    Item getItemById(@PathVariable("id") String id);

}
