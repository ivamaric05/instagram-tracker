package com.example.instagram_tracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class FollowerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int followerCount;

    private LocalDateTime checkedAt;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public FollowerHistory() {
    }

    public FollowerHistory(
            int followerCount,
            LocalDateTime checkedAt,
            Profile profile
    ) {
        this.followerCount = followerCount;
        this.checkedAt = checkedAt;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
