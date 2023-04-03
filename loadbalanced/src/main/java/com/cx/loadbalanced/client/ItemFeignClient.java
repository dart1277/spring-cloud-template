package com.cx.loadbalanced.client;


import com.cx.loadbalanced.dto.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "sync-cloud-worker", contextId = "ItemFeignClientCtxId")
public interface ItemFeignClient {

    @GetMapping(value = "web/item/{id}", produces = "application/json")
    Item getItemById(@PathVariable("id") String id);

}

