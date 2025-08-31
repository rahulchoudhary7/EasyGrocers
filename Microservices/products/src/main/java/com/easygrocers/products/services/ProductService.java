package com.easygrocers.products.services;



import com.easygrocers.products.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProductService  {

    default List<ProductDTO> getAllProducts() {
        return null;
    }

    default ProductDTO getProductById(UUID id) {
        return null;
    }


    default ProductDTO createProduct(ProductDTO productDTO) {
        return null;
    }


    default ProductDTO updateProduct(UUID id, ProductDTO productDTO) {
       return null;
    }


    public default void deleteProduct(UUID id) {}

}
