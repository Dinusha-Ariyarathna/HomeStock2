package com.skillshare.platform.service.impl;

import com.skillshare.platform.dto.InventoryItemDto;
import com.skillshare.platform.model.InventoryItem;
import com.skillshare.platform.model.User;
import com.skillshare.platform.repository.InventoryItemRepository;
import com.skillshare.platform.service.InventoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
    private final InventoryItemRepository repo;

    public InventoryServiceImpl(InventoryItemRepository repo) {
        this.repo = repo;
    }

    @Override
    public InventoryItemDto createItem(InventoryItemDto dto, User user) {
        InventoryItem item = new InventoryItem(
                user,
                dto.getName(),
                dto.getQuantity(),
                dto.getUnit(),
                dto.getExpiryDate(),
                dto.getNotes()
        );
        InventoryItem saved = repo.save(item);
        return toDto(saved);
    }

    @Override
    public InventoryItemDto updateItem(Long id, InventoryItemDto dto, User user) {
        InventoryItem item = repo.findById(id)
                .filter(i -> i.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setName(dto.getName());
        item.setQuantity(dto.getQuantity());
        item.setUnit(dto.getUnit());
        item.setExpiryDate(dto.getExpiryDate());
        item.setNotes(dto.getNotes());
        // usedDate remains unchanged here
        return toDto(item);
    }

    @Override
    public void deleteItem(Long id, User user) {
        InventoryItem item = repo.findById(id)
                .filter(i -> i.getUser().equals(user))
                .orElseThrow(() -> new RuntimeException("Item not found"));
        repo.delete(item);
    }

    @Override
    public List<InventoryItemDto> listItems(User user) {
        return repo.findByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryItemDto> listExpiring(User user, int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate cutoff = today.plusDays(daysAhead);
        return repo.findByUserAndExpiryDateBetween(user, today, cutoff).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private InventoryItemDto toDto(InventoryItem item) {
        InventoryItemDto dto = new InventoryItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnit(item.getUnit());
        dto.setExpiryDate(item.getExpiryDate());
        dto.setNotes(item.getNotes());
        return dto;
    }
}
