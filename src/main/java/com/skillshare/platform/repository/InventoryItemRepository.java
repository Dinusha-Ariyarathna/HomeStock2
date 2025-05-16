package com.skillshare.platform.repository;

import com.skillshare.platform.model.InventoryItem;
import com.skillshare.platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByUser(User user);
    List<InventoryItem> findByUserAndExpiryDateBetween(User user, LocalDate start, LocalDate end);
}