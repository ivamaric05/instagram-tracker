package com.example.instagram_tracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FollowingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime checkedAt;

    private int followingCount;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public FollowingHistory() {
    }

    public FollowingHistory(
            LocalDateTime checkedAt,
            int followingCount,
            Profile profile
    ) {
        this.checkedAt = checkedAt;
        this.followingCount = followingCount;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
