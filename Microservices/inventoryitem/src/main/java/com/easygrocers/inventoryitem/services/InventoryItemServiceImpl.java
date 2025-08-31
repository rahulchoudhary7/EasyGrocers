package com.easygrocers.inventoryitem.services;

import com.easygrocers.inventoryitem.clients.ProductClient;
import com.easygrocers.inventoryitem.dto.InventoryItemDTO;
import com.easygrocers.inventoryitem.entities.InventoryItem;
import com.easygrocers.inventoryitem.exceptions.NotAuthorizedException;
import com.easygrocers.inventoryitem.exceptions.ResourceNotFoundException;
import com.easygrocers.inventoryitem.external.Product;
import com.easygrocers.inventoryitem.repositories.InventoryItemRepo;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InventoryItemServiceImpl implements  InventoryItemService{
    @Autowired
    InventoryItemRepo inventoryItemRepo;

    @Autowired
    ProductClient productClient;


    @Override
    @Transactional(readOnly = true)
    public List<InventoryItemDTO> getInventoryItemsBySellerId(String sellerId) {
        List<InventoryItem> inventoryItems = inventoryItemRepo.findBySellerId(sellerId);
        return inventoryItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryItemDTO getInventoryItemById(UUID inventoryItemId) {
        InventoryItem existingInventoryItem = inventoryItemRepo.findById(inventoryItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + inventoryItemId));

        return convertToDTO(existingInventoryItem);
    }

    @Override
    public InventoryItemDTO createInventoryItem(InventoryItem inventoryItem, String userRole, String userId) {
        if (!userRole.equals("SELLER") && !userRole.equals("ADMIN")) {
            throw new NotAuthorizedException("Not Authorized: Invalid user role");
        }

        if (userRole.equals("SELLER") && !inventoryItem.getSellerId().equals(userId)) {
            System.out.println(userId + " | " + inventoryItem.getSellerId());
            throw new NotAuthorizedException("Not Authorized: Seller ID mismatch");
        }

        InventoryItem savedInventoryItem = inventoryItemRepo.save(inventoryItem);

        return convertToDTO(savedInventoryItem);
    }

    @Override
    public InventoryItemDTO updateInventoryItem (InventoryItem inventoryItem, String userRole, UUID inventoryItemId, String userId) {

        if (!userRole.equals("SELLER") && !userRole.equals("ADMIN")) {
            throw new NotAuthorizedException("Not Authorized: Invalid user role");
        }


        if(!inventoryItem.getSellerId().equals(userId)){
            throw new NotAuthorizedException("Not Authorized: Seller ID mismatch");
        }

        InventoryItem existingInventoryItem = inventoryItemRepo.findById(inventoryItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + inventoryItemId));


        if (userRole.equals("SELLER") && !existingInventoryItem.getSellerId().equals(userId)) {
            throw new NotAuthorizedException("Not Authorized: Seller ID mismatch");
        }

        InventoryItem updatedInventoryItem = inventoryItemRepo.save(inventoryItem);

        return convertToDTO(updatedInventoryItem);
    }

    @Override
    public void deleteInventoryItem(UUID inventoryItemId, String userRole, String userId) {

        if (!userRole.equals("SELLER") && !userRole.equals("ADMIN")) {
            throw new NotAuthorizedException("Not Authorized: Invalid user role");
        }

        InventoryItem existingInventoryItem = inventoryItemRepo.findById(inventoryItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + inventoryItemId));

        if (userRole.equals("SELLER") && !existingInventoryItem.getSellerId().equals(userId)) {
            throw new NotAuthorizedException("Not Authorized: Seller ID mismatch");
        }

        inventoryItemRepo.deleteById(inventoryItemId);
    }

    public InventoryItem convertToEntity(InventoryItemDTO dto) {
        InventoryItem inventoryItem = new InventoryItem();

        BeanUtils.copyProperties(dto, inventoryItem);

        return inventoryItem;
    }

    public InventoryItemDTO convertToDTO(InventoryItem inventoryItem) {
        InventoryItemDTO inventoryItemDTO = new InventoryItemDTO();

        Product product = productClient.getProduct(inventoryItem.getProductId());

        inventoryItemDTO.setProduct(product);

        BeanUtils.copyProperties(inventoryItem, inventoryItemDTO);

        return inventoryItemDTO;
    }

}
