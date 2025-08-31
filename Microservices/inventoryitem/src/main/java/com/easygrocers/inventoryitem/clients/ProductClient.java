package com.easygrocers.inventoryitem.clients;

import com.easygrocers.inventoryitem.external.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "product-service", url = "http://localhost:8081")
public interface ProductClient {

    @GetMapping("/api/v1/public/products/getProduct/{productId}")
    Product getProduct(@PathVariable("productId") UUID productId);
}
