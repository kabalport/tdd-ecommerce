package com.example.tddecommerce.member.concurrency.isolation;

import jakarta.persistence.*;

@Entity
@Table(name = "isolation_user_accounts")
public class IsolationUserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private int points;

    public IsolationUserAccount() {
    }

    public IsolationUserAccount(String username, int points) {
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
