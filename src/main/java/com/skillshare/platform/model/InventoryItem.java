package com.skillshare.platform.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_items")
@Getter
@Setter
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Owning user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String unit;   // e.g. "pcs", "kg", "L"

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime addedDate;

    @Column(nullable = false)
    private LocalDate expiryDate;

    private LocalDate usedDate;    // when the user marks it as used

    private String notes;

    // --- Constructors ---
    public InventoryItem() {}
    public InventoryItem(User user, String name, Integer quantity, String unit,
                         LocalDate expiryDate, String notes) {
        this.user       = user;
        this.name       = name;
        this.quantity   = quantity;
        this.unit       = unit;
        this.expiryDate = expiryDate;
        this.notes      = notes;
    }

    // --- Getters & Setters ---
    // (generate all: id, user, name, quantity, unit,
    //  addedDate, expiryDate, usedDate, notes)
}
