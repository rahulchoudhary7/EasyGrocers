package com.easygrocers.inventoryitem.controllers;


import com.easygrocers.inventoryitem.dto.InventoryItemDTO;
import com.easygrocers.inventoryitem.services.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/inventoryItems")
public class InventoryItemPublicController {
    @Autowired
    InventoryItemService inventoryItemService;

    @GetMapping("/getInventoryItemById/{inventoryItemId}")
    ResponseEntity<InventoryItemDTO> getInventoryItemById(@PathVariable UUID inventoryItemId){
        return ResponseEntity.ok(inventoryItemService.getInventoryItemById(inventoryItemId));
    }

    @GetMapping("/getInventoryItemsBySellerId/{sellerId}")
    ResponseEntity<List<InventoryItemDTO>> getInventoryItemBySellerId (@PathVariable String sellerId){
        return ResponseEntity.ok(inventoryItemService.getInventoryItemsBySellerId(sellerId));
    }
}
