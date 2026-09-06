package com.example.instagram_tracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private LocalDateTime lastSeen;

    private boolean isNew;

    private boolean isFollowing;

    private String category = "Unknown";

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Following() {
    }

    public Following(String username, Profile profile) {
        this.username = username;
        this.profile = profile;
        this.lastSeen = LocalDateTime.now();
        this.isNew = false;
        this.isFollowing = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}

