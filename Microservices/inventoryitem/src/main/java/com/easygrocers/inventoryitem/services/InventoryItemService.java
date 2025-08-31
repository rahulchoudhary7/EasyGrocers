package com.easygrocers.inventoryitem.services;

import com.easygrocers.inventoryitem.dto.InventoryItemDTO;
import com.easygrocers.inventoryitem.entities.InventoryItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface InventoryItemService {

    List<InventoryItemDTO> getInventoryItemsBySellerId(String sellerID);

    InventoryItemDTO getInventoryItemById(UUID inventoryItemId);

    InventoryItemDTO createInventoryItem(InventoryItem inventoryItem, String userRole, String sellerId);

    InventoryItemDTO updateInventoryItem (InventoryItem inventoryItem, String userRole, UUID inventoryItemId, String sellerId);

    void deleteInventoryItem (UUID inventoryItemId, String userRole, String sellerId);

}
