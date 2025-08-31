package com.easygrocers.inventoryitem.controllers;


import java.util.List;
import java.util.UUID;

import com.easygrocers.inventoryitem.dto.InventoryItemDTO;
import com.easygrocers.inventoryitem.entities.InventoryItem;
import com.easygrocers.inventoryitem.services.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/seller/inventoryItems")
public class InventoryItemController {
    @Autowired
    InventoryItemService inventoryItemService;


    @PostMapping(value = "/createInventoryItem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryItemDTO> createInventoryItem(@RequestHeader("X-User-Roles") String userRole, @RequestHeader("X-User-Id") String userId,  @Valid @RequestBody InventoryItem inventoryItem) {

        InventoryItemDTO createdInventoryItemDTO = inventoryItemService.createInventoryItem(inventoryItem, userRole, userId);

        return ResponseEntity.ok(createdInventoryItemDTO);

    }

    @PutMapping("/updateInventoryItem/{inventoryItemId}")
    public ResponseEntity<InventoryItemDTO> updateInventoryItem(@PathVariable UUID inventoryItemId,
                                                                @Valid @RequestBody InventoryItem inventoryItem, @RequestHeader("X-User-Roles") String userRole, @RequestHeader("X-User-Id") String userId) {


        System.out.println("Seller id - " + userId);
        InventoryItemDTO updaInventoryItemDTO = inventoryItemService.updateInventoryItem(inventoryItem, userRole, inventoryItemId, userId );

        return ResponseEntity.ok(updaInventoryItemDTO);
    }

    @DeleteMapping("/deleteInventoryItem/{inventoryItemId}")
    public ResponseEntity<String> deleteInventoryItem(@PathVariable  UUID inventoryItemId, @RequestHeader("X-User-Roles") String userRole, @RequestHeader("X-User-Id") String userId) {

        inventoryItemService.deleteInventoryItem(inventoryItemId, userRole, userId);

        return ResponseEntity.ok("Inventory item deleted");
    }

}

