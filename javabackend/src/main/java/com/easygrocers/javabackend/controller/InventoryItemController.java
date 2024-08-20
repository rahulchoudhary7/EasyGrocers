package com.easygrocers.javabackend.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easygrocers.javabackend.dto.InventoryItemDTO;
import com.easygrocers.javabackend.service.InventoryItemService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/InventoryItem")
public class InventoryItemController {
    @Autowired
    InventoryItemService inventoryItemService;

    @GetMapping("/getInventoryItem")
    public ResponseEntity<InventoryItemDTO> getInventoryItem(@RequestParam UUID inventoryItemId) {
        InventoryItemDTO inventoryItemDTO = inventoryItemService.getInventoryItem(inventoryItemId);

        return ResponseEntity.ok(inventoryItemDTO);
    }

    @PostMapping("/createInventoryItem")
    public ResponseEntity<InventoryItemDTO> createInventoryItem(@Valid @RequestBody InventoryItemDTO inventoryItemDTO) {
        InventoryItemDTO createdInventoryItemDTO = inventoryItemService.createInventoryItem(inventoryItemDTO);

        return ResponseEntity.ok(createdInventoryItemDTO);

    }

    @PutMapping("/updateInventoryItem")
    public ResponseEntity<InventoryItemDTO> updateInventoryItem(@RequestParam UUID inventoryItemId,
            @Valid @RequestBody InventoryItemDTO inventoryItemDTO) {
        InventoryItemDTO updaInventoryItemDTO = inventoryItemService.updateInventoryItem(inventoryItemId, inventoryItemDTO);

        return ResponseEntity.ok(updaInventoryItemDTO);
    }

    @DeleteMapping("/deleteInventoryItem")
    public ResponseEntity<String> deleteInventoryItem(@RequestParam UUID inventoryItemId) {
        inventoryItemService.deleteInventoryItem(inventoryItemId);

        return ResponseEntity.ok("Inventory item deleted");
    }

}
