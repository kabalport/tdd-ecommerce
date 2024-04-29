package com.example.tddecommerce.member.concurrency.optimistic;

import jakarta.persistence.*;

@Entity
@Table(name = "optimistic_user_accounts")
public class OptimisticUserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private int points;

    @Version
    private int version;

    public OptimisticUserAccount() {
    }

    public OptimisticUserAccount(String username, int points) {
        this.username = username;
        this.points = points;
    }

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

    public int getVersion() {
        return version;
    }
}
