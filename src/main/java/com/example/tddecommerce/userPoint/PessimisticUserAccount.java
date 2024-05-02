package com.example.tddecommerce.userPoint;

import jakarta.persistence.*;

@Entity
@Table(name = "pessimistic_user_accounts")
public class PessimisticUserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private int points;

    public PessimisticUserAccount() {
    }

    public PessimisticUserAccount(String username, int points) {
        this.username = username;
        this.points = points;
    }

    // Standard getters and setters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
