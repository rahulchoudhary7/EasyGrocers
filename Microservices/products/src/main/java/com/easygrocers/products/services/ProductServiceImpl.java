package com.easygrocers.products.services;

import com.easygrocers.products.dto.ProductDTO;
import com.easygrocers.products.entities.Product;
import com.easygrocers.products.exceptions.ResourceNotFoundException;
import com.easygrocers.products.repositories.ProductRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Transactional(readOnly = true)
    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductDTO getProductById(UUID id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToDTO(product);
    }

    @Transactional
    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);

        Product savedProduct = productRepo.save(product);
        return convertToDTO(savedProduct);
    }

    @Transactional
    @Override
    public ProductDTO updateProduct(UUID id, ProductDTO productDTO) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        BeanUtils.copyProperties(productDTO, existingProduct, "id");
        Product updatedProduct = productRepo.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID id) {
        if (!productRepo.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepo.deleteById(id);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        BeanUtils.copyProperties(dto, product);
        return product;
    }
}

