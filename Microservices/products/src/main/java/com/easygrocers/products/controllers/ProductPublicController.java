package com.easygrocers.products.controllers;


import com.easygrocers.products.dto.ProductDTO;
import com.easygrocers.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/products")
public class ProductPublicController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> productDTOList = productService.getAllProducts();

        return ResponseEntity.ok(productDTOList);
    }

    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID productId) {
        ProductDTO product = productService.getProductById(productId);

        return ResponseEntity.ok(product);
    }

}

