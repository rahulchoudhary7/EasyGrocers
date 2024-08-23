package com.easygrocers.javabackend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easygrocers.javabackend.dto.InventoryItemDTO;
import com.easygrocers.javabackend.entity.UserDetails;
import com.easygrocers.javabackend.service.InventoryItemService;

import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/getInventoryItemsForSeller")
    public ResponseEntity<List<InventoryItemDTO>> getAllInventoryItem(HttpServletRequest request,
            @RequestParam String sellerId) {

        List<InventoryItemDTO> inventoryItemDTO = inventoryItemService
                .getInventoryItemsBySellerId(sellerId);

        System.out.println(inventoryItemDTO);

        return ResponseEntity.ok(inventoryItemDTO);
    }

    @PostMapping(value = "/createInventoryItem", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InventoryItemDTO> createInventoryItem(@Valid @RequestBody InventoryItemDTO inventoryItemDTO,
            HttpServletRequest request) {

        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");

        System.out.println(userDetails.getUserId() +"\n\n\n=========");

        InventoryItemDTO createdInventoryItemDTO = inventoryItemService.createInventoryItem(inventoryItemDTO,
                userDetails);

        return ResponseEntity.ok(createdInventoryItemDTO);

    }

    @PutMapping("/updateInventoryItem")
    public ResponseEntity<InventoryItemDTO> updateInventoryItem(@RequestParam UUID inventoryItemId,
            @Valid @RequestBody InventoryItemDTO inventoryItemDTO, HttpServletRequest request) {

        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");
        System.out.println(userDetails.getUserId() +"\n\n\n=========");
        InventoryItemDTO updaInventoryItemDTO = inventoryItemService.updateInventoryItem(inventoryItemId,
                inventoryItemDTO, userDetails);

        return ResponseEntity.ok(updaInventoryItemDTO);
    }

    @DeleteMapping("/deleteInventoryItem")
    public ResponseEntity<String> deleteInventoryItem(@RequestParam UUID inventoryItemId, HttpServletRequest request) {

        UserDetails userDetails = (UserDetails) request.getAttribute("userDetails");

        inventoryItemService.deleteInventoryItem(inventoryItemId, userDetails);

        return ResponseEntity.ok("Inventory item deleted");
    }

}
