package com.example.instagram_tracker.service;

import com.example.instagram_tracker.model.Follower;
import com.example.instagram_tracker.model.FollowerHistory;
import com.example.instagram_tracker.model.FollowerEvent;
import com.example.instagram_tracker.model.Following;
import com.example.instagram_tracker.model.FollowingHistory;
import com.example.instagram_tracker.model.Profile;
import com.example.instagram_tracker.repository.FollowerEventRepository;
import com.example.instagram_tracker.repository.FollowerRepository;
import com.example.instagram_tracker.repository.FollowerHistoryRepository;
import com.example.instagram_tracker.repository.FollowingRepository;
import com.example.instagram_tracker.repository.FollowingHistoryRepository;
import com.example.instagram_tracker.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class TrackerService {

    private final ProfileRepository profileRepository;
    private final FollowerRepository followerRepository;
    private final FollowerEventRepository eventRepository;
    private final FollowingRepository followingRepository;
    private final FollowerHistoryRepository followerHistoryRepository;
    private final FollowingHistoryRepository followingHistoryRepository;

    public TrackerService(
            ProfileRepository profileRepository,
            FollowerRepository followerRepository,
            FollowerEventRepository eventRepository,
            FollowingRepository followingRepository,
            FollowerHistoryRepository followerHistoryRepository,
            FollowingHistoryRepository followingHistoryRepository
    ) {
        this.profileRepository = profileRepository;
        this.followerRepository = followerRepository;
        this.eventRepository = eventRepository;
        this.followingRepository = followingRepository;
        this.followerHistoryRepository = followerHistoryRepository;
        this.followingHistoryRepository = followingHistoryRepository;
    }


    // =========================================================
    // FOLLOWERS
    // =========================================================

    public Map<String, Set<String>> checkFollowers(
            String username,
            String followersText
    ) {

        Profile profile = profileRepository
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

        Set<String> currentFollowers =
                parseList(followersText);

        List<Follower> existingFollowers =
                followerRepository.findByProfile(profile);

        boolean firstCheck =
                profile.getLastCheckedFollowers() == null;

        Set<String> oldFollowers =
                new HashSet<>();

        for (Follower follower : existingFollowers) {

            if (follower.isFollowing()) {

                oldFollowers.add(
                        follower.getUsername()
                );
            }

            follower.setNew(false);
            followerRepository.save(follower);
        }

        Set<String> newFollowers =
                new HashSet<>();

        Set<String> unfollowed =
                new HashSet<>();

        LocalDateTime now =
                LocalDateTime.now();


        // PRVA PROVERA
        if (firstCheck) {

            for (String followerUsername :
                    currentFollowers) {

                Follower follower =
                        new Follower(
                                followerUsername,
                                profile
                        );

                follower.setFollowing(true);
                follower.setNew(false);
                follower.setLastSeen(now);

                followerRepository.save(follower);
            }
        }


        // SLEDEĆE PROVERE
        else {

            newFollowers =
                    new HashSet<>(currentFollowers);

            newFollowers.removeAll(oldFollowers);


            unfollowed =
                    new HashSet<>(oldFollowers);

            unfollowed.removeAll(currentFollowers);


            // NOVI FOLLOWERI
            for (String newUsername :
                    newFollowers) {

                Optional<Follower> existing =
                        followerRepository
                                .findByProfileAndUsername(
                                        profile,
                                        newUsername
                                );

                Follower follower;

                if (existing.isPresent()) {

                    follower = existing.get();

                } else {

                    follower =
                            new Follower(
                                    newUsername,
                                    profile
                            );
                }

                follower.setFollowing(true);
                follower.setNew(true);
                follower.setLastSeen(now);

                followerRepository.save(follower);

                FollowerEvent event =
                        new FollowerEvent(
                                "FOLLOW",
                                profile,
                                follower
                        );

                eventRepository.save(event);
            }


            // POSTOJEĆI FOLLOWERI
            for (Follower follower :
                    existingFollowers) {

                if (currentFollowers.contains(
                        follower.getUsername()
                )) {

                    follower.setFollowing(true);
                    follower.setNew(false);
                    follower.setLastSeen(now);

                    followerRepository.save(follower);
                }
            }


            // UNFOLLOWED
            for (Follower follower :
                    existingFollowers) {

                if (unfollowed.contains(
                        follower.getUsername()
                )) {

                    follower.setFollowing(false);
                    follower.setNew(false);

                    followerRepository.save(follower);

                    FollowerEvent event =
                            new FollowerEvent(
                                    "UNFOLLOW",
                                    profile,
                                    follower
                            );

                    eventRepository.save(event);
                }
            }
        }

        profile.setLastCheckedFollowers(now);
        profileRepository.save(profile);


        // =====================================================
        // SAVE FOLLOWER HISTORY
        // =====================================================

        FollowerHistory history =
                new FollowerHistory(
                        currentFollowers.size(),
                        now,
                        profile
                );

        followerHistoryRepository.save(history);


        Map<String, Set<String>> result =
                new HashMap<>();

        result.put(
                "newFollowers",
                newFollowers
        );

        result.put(
                "unfollowed",
                unfollowed
        );

        return result;
    }


    // =========================================================
    // FOLLOWING
    // =========================================================

    public Map<String, Set<String>> checkFollowing(
            String username,
            String followingText
    ) {

        Profile profile = profileRepository
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

        Set<String> currentFollowing =
                parseList(followingText);

        List<Following> existingFollowing =
                followingRepository.findByProfile(profile);

        boolean firstCheck =
                profile.getLastCheckedFollowing() == null;

        Set<String> oldFollowing =
                new HashSet<>();

        for (Following following :
                existingFollowing) {

            if (following.isFollowing()) {

                oldFollowing.add(
                        following.getUsername()
                );
            }

            following.setNew(false);

            followingRepository.save(following);
        }

        Set<String> newFollowing =
                new HashSet<>();

        Set<String> unfollowed =
                new HashSet<>();

        LocalDateTime now =
                LocalDateTime.now();


        // PRVA PROVERA
        if (firstCheck) {

            for (String followingUsername :
                    currentFollowing) {

                Following following =
                        new Following(
                                followingUsername,
                                profile
                        );

                following.setFollowing(true);
                following.setNew(false);
                following.setLastSeen(now);

                followingRepository.save(following);
            }
        }


        // SLEDEĆE PROVERE
        else {

            // NOVI FOLLOWING
            newFollowing =
                    new HashSet<>(currentFollowing);

            newFollowing.removeAll(oldFollowing);


            // VIŠE NE PRATIŠ
            unfollowed =
                    new HashSet<>(oldFollowing);

            unfollowed.removeAll(currentFollowing);


            // DODAVANJE NOVIH
            for (String newUsername :
                    newFollowing) {

                Optional<Following> existing =
                        followingRepository
                                .findByProfileAndUsername(
                                        profile,
                                        newUsername
                                );

                Following following;

                if (existing.isPresent()) {

                    following = existing.get();

                } else {

                    following =
                            new Following(
                                    newUsername,
                                    profile
                            );
                }

                following.setFollowing(true);
                following.setNew(true);
                following.setLastSeen(now);

                followingRepository.save(following);
            }


            // POSTOJEĆI
            for (Following following :
                    existingFollowing) {

                if (currentFollowing.contains(
                        following.getUsername()
                )) {

                    following.setFollowing(true);
                    following.setNew(false);
                    following.setLastSeen(now);

                    followingRepository.save(following);
                }
            }


            // VIŠE NE PRATIŠ
            for (Following following :
                    existingFollowing) {

                if (unfollowed.contains(
                        following.getUsername()
                )) {

                    following.setFollowing(false);
                    following.setNew(false);

                    followingRepository.save(following);
                }
            }
        }

        profile.setLastCheckedFollowing(now);
        profileRepository.save(profile);

        // =====================================================
        // SAVE FOLLOWING HISTORY
        // =====================================================

        FollowingHistory history =
                new FollowingHistory(
                        now,
                        currentFollowing.size(),
                        profile
                );

        followingHistoryRepository.save(history);

        Map<String, Set<String>> result =
                new HashMap<>();

        result.put(
                "newFollowing",
                newFollowing
        );

        result.put(
                "unfollowedFollowing",
                unfollowed
        );

        return result;
    }


    // =========================================================
    // PARSING
    // =========================================================

    private Set<String> parseList(String text) {

        Set<String> result =
                new HashSet<>();

        if (text == null ||
                text.trim().isEmpty()) {

            return result;
        }

        String[] lines =
                text.split("\\r?\\n");

        for (String line :
                lines) {

            String username =
                    line
                            .trim()
                            .replace("@", "")
                            .trim()
                            .toLowerCase();

            if (!username.isEmpty()) {

                result.add(username);
            }
        }

        return result;
    }
}