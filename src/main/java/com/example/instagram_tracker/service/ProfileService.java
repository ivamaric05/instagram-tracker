package com.example.instagram_tracker.service;

import com.example.instagram_tracker.model.Follower;
import com.example.instagram_tracker.model.Following;
import com.example.instagram_tracker.model.Profile;
import com.example.instagram_tracker.repository.FollowerRepository;
import com.example.instagram_tracker.repository.FollowingRepository;
import com.example.instagram_tracker.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final FollowerRepository followerRepository;
    private final FollowingRepository followingRepository;

    public ProfileService(
            ProfileRepository profileRepository,
            FollowerRepository followerRepository,
            FollowingRepository followingRepository
    ) {
        this.profileRepository = profileRepository;
        this.followerRepository = followerRepository;
        this.followingRepository = followingRepository;
    }

    public Profile addProfile(String username) {

        username = username
                .trim()
                .toLowerCase()
                .replace("@", "");

        if (username.isEmpty()) {
            throw new IllegalArgumentException(
                    "Username cannot be empty."
            );
        }

        if (profileRepository.existsByUsername(username)) {
            throw new IllegalArgumentException(
                    "Profile is already being tracked."
            );
        }

        Profile profile = new Profile(username);

        return profileRepository.save(profile);
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getProfile(String username) {

        return profileRepository
                .findByUsername(
                        username
                                .trim()
                                .toLowerCase()
                                .replace("@", "")
                )
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Profile not found."
                        )
                );
    }

    // =========================================================
    // FOLLOWER CATEGORIES
    // =========================================================

    public void updateFollowerCategory(
            Long followerId,
            String category
    ) {
        Follower follower =
                followerRepository.findById(followerId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Follower not found."
                                )
                        );

        follower.setCategory(category);

        followerRepository.save(follower);
    }

    public void updateFollowerCategories(
            Map<Long, String> categories
    ) {
        for (Map.Entry<Long, String> entry :
                categories.entrySet()) {

            updateFollowerCategory(
                    entry.getKey(),
                    entry.getValue()
            );
        }
    }

    public long countFollowersByCategory(
            Profile profile,
            String category
    ) {
        return profile.getFollowers()
                .stream()
                .filter(Follower::isFollowing)
                .filter(follower ->
                        category.equals(follower.getCategory())
                )
                .count();
    }

    // =========================================================
    // FOLLOWING CATEGORIES
    // =========================================================

    public void updateFollowingCategory(
            Long followingId,
            String category
    ) {
        Following following =
                followingRepository.findById(followingId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Following not found."
                                )
                        );

        following.setCategory(category);

        followingRepository.save(following);
    }

    public void updateFollowingCategories(
            Map<Long, String> categories
    ) {
        for (Map.Entry<Long, String> entry :
                categories.entrySet()) {

            updateFollowingCategory(
                    entry.getKey(),
                    entry.getValue()
            );
        }
    }

    public long countFollowingByCategory(
            Profile profile,
            String category
    ) {
        return profile.getFollowing()
                .stream()
                .filter(Following::isFollowing)
                .filter(following ->
                        category.equals(following.getCategory())
                )
                .count();
    }
}