package com.example.tddecommerce.domain.user.business.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Table(name="`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(String name, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User() {

    }

    public User(long userId, String name, String email) {

        this.userId = userId;
        this.name = name;
        this.email = email;
    }


    public void updateName(String updateName) {
        this.name = updateName;
    }

    public boolean isActive() {
        return true;
    }

    public boolean canPlaceOrder() {
        return true;
    }
}
