package com.easygrocers.javabackend.service;

import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easygrocers.javabackend.dto.InventoryItemDTO;
import com.easygrocers.javabackend.entity.InventoryItem;
import com.easygrocers.javabackend.exception.ResourceNotFoundException;
import com.easygrocers.javabackend.repository.InventoryItemRepo;

import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class InventoryItemService {
    @Autowired
    InventoryItemRepo inventoryItemRepo;

    @Transactional(readOnly = true)
    public List<InventoryItemDTO> getAllInventoryItem(UUID id) {
        return inventoryItemRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InventoryItemDTO getInventoryItem(UUID id) {
        InventoryItem existingInventoryItem = inventoryItemRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        return convertToDTO(existingInventoryItem);
    }

    @Transactional
    public InventoryItemDTO createInventoryItem(InventoryItemDTO inventoryItemDTO) {
        InventoryItem inventoryItem = convertToEntity(inventoryItemDTO);

        InventoryItem savedInventoryItem = inventoryItemRepo.save(inventoryItem);

        return convertToDTO(savedInventoryItem);
    }

    @Transactional
    public InventoryItemDTO updateInventoryItem(UUID id, InventoryItemDTO inventoryItemDTO) {

        InventoryItem existingInventoryItem = inventoryItemRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        BeanUtils.copyProperties(inventoryItemDTO, existingInventoryItem, "id");

        InventoryItem updatedInventoryItem = inventoryItemRepo.save(existingInventoryItem);

        return convertToDTO(updatedInventoryItem);
    }

    @Transactional
    public void deleteInventoryItem(UUID id) {
        if (!inventoryItemRepo.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with id: " + id);
        }

        inventoryItemRepo.deleteById(id);
    }

    public InventoryItem convertToEntity(InventoryItemDTO dto) {
        InventoryItem inventoryItem = new InventoryItem();

        BeanUtils.copyProperties(dto, inventoryItem);

        return inventoryItem;
    }

    public InventoryItemDTO convertToDTO(InventoryItem inventoryItem) {
        InventoryItemDTO inventoryItemDTO = new InventoryItemDTO();

        BeanUtils.copyProperties(inventoryItem, inventoryItemDTO);

        return inventoryItemDTO;
    }
}
