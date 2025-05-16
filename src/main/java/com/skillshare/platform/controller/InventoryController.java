package com.skillshare.platform.controller;

import com.skillshare.platform.dto.InventoryItemDto;
import com.skillshare.platform.model.User;
import com.skillshare.platform.repository.UserRepository;
import com.skillshare.platform.service.InventoryService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final UserRepository userRepo;

    public InventoryController(InventoryService inventoryService, UserRepository userRepo) {
        this.inventoryService = inventoryService;
        this.userRepo = userRepo;
    }

    // Helper: map OAuth2User → our User entity
    private User getCurrentUser(@AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public List<InventoryItemDto> listAll(@AuthenticationPrincipal OAuth2User oauthUser) {
        return inventoryService.listItems(getCurrentUser(oauthUser));
    }

    @GetMapping("/expiring")
    public List<InventoryItemDto> listExpiring(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @RequestParam(defaultValue = "3") int daysAhead
    ) {
        return inventoryService.listExpiring(getCurrentUser(oauthUser), daysAhead);
    }

    @PostMapping
    public ResponseEntity<InventoryItemDto> create(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @Valid @RequestBody InventoryItemDto dto
    ) {
        InventoryItemDto created = inventoryService.createItem(dto, getCurrentUser(oauthUser));
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItemDto> update(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @PathVariable Long id,
            @Valid @RequestBody InventoryItemDto dto
    ) {
        InventoryItemDto updated = inventoryService.updateItem(id, dto, getCurrentUser(oauthUser));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal OAuth2User oauthUser,
            @PathVariable Long id
    ) {
        inventoryService.deleteItem(id, getCurrentUser(oauthUser));
        return ResponseEntity.noContent().build();
    }
}