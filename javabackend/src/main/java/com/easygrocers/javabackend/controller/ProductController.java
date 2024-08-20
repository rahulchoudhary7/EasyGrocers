package com.easygrocers.javabackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easygrocers.javabackend.dto.ProductDTO;
import com.easygrocers.javabackend.service.ProductService;

import jakarta.validation.Valid;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/createProduct")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO product) {
        ProductDTO newProduct = productService.createProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping("/getProduct")
    public ResponseEntity<ProductDTO> getProductById(@RequestParam UUID productId) {
        ProductDTO product = productService.getProductById(productId);

        return ResponseEntity.ok(product);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductDTO> updateProductById(@RequestParam UUID productId,
            @RequestBody ProductDTO productDTO) {

        ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String> deleteProductById(@RequestParam UUID productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.ok("Product deleted successfully");
    }

}
