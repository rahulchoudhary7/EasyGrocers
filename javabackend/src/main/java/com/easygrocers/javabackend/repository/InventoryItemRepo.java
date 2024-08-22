package com.easygrocers.javabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easygrocers.javabackend.entity.InventoryItem;
import java.util.UUID;
import java.util.List;

@Repository
public interface InventoryItemRepo extends JpaRepository<InventoryItem, UUID> {

    public List<InventoryItem> findBySellerId(String sellerId);

}
