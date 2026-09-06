package com.example.instagram_tracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private LocalDateTime lastCheckedFollowers;
    private LocalDateTime lastCheckedFollowing;

    @OneToMany(
            mappedBy = "profile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Follower> followers = new ArrayList<>();

    @OneToMany(
            mappedBy = "profile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Following> following = new ArrayList<>();

    @OneToMany(
            mappedBy = "profile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FollowerHistory> followerHistory = new ArrayList<>();

    @OneToMany(
            mappedBy = "profile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<FollowingHistory> followingHistory = new ArrayList<>();

    public Profile() {
    }

    public Profile(String username) {
        this.username = username;
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

    public LocalDateTime getLastCheckedFollowers() {
        return lastCheckedFollowers;
    }

    public void setLastCheckedFollowers(
            LocalDateTime lastCheckedFollowers
    ) {
        this.lastCheckedFollowers = lastCheckedFollowers;
    }

    public LocalDateTime getLastCheckedFollowing() {
        return lastCheckedFollowing;
    }

    public void setLastCheckedFollowing(
            LocalDateTime lastCheckedFollowing
    ) {
        this.lastCheckedFollowing = lastCheckedFollowing;
    }

    public List<Follower> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Follower> followers) {
        this.followers = followers;
    }

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(List<Following> following) {
        this.following = following;
    }

    public List<FollowerHistory> getFollowerHistory() {
        return followerHistory;
    }

    public void setFollowerHistory(
            List<FollowerHistory> followerHistory
    ) {
        this.followerHistory = followerHistory;
    }

    public List<FollowingHistory> getFollowingHistory() {
        return followingHistory;
    }

    public void setFollowingHistory(List<FollowingHistory> followingHistory) {
        this.followingHistory = followingHistory;
    }
}