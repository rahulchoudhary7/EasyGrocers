package com.easygrocers.products.controllers;

import com.easygrocers.products.dto.ProductDTO;
import com.easygrocers.products.exceptions.NotAuthorizedException;
import com.easygrocers.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/createProduct")
    public ResponseEntity<ProductDTO> createProduct(@RequestHeader("X-User-Roles") String userRoles, @Valid @RequestBody ProductDTO product) {
        if (!userRoles.contains("ADMIN")) {
            throw new NotAuthorizedException("Unauthorized");
        }
        ProductDTO newProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("updateProduct/{productId}")
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable UUID productId,
                                                        @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("deleteProduct/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
