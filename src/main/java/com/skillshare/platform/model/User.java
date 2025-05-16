package com.skillshare.platform.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String name;
    private String authProvider; // e.g. "GOOGLE"

    // Constructors, getters/setters...

    public User() {}
    public User(String email, String name, String authProvider) {
        this.email = email;
        this.name = name;
        this.authProvider = authProvider;
    }
    // ... getters and setters ...
}