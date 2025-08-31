package com.easygrocers.inventoryitem.repositories;

import com.easygrocers.inventoryitem.entities.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface InventoryItemRepo extends JpaRepository<InventoryItem, UUID> {

    public List<InventoryItem> findBySellerId(String sellerId);

}
