package com.skillshare.platform.service;

import com.skillshare.platform.dto.InventoryItemDto;
import com.skillshare.platform.model.User;

import java.util.List;

public interface InventoryService {
    InventoryItemDto createItem(InventoryItemDto dto, User user);
    InventoryItemDto updateItem(Long id, InventoryItemDto dto, User user);
    void deleteItem(Long id, User user);
    List<InventoryItemDto> listItems(User user);
    List<InventoryItemDto> listExpiring(User user, int daysAhead);
}
