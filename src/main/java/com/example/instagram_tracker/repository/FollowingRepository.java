package com.example.instagram_tracker.repository;

import com.example.instagram_tracker.model.Following;
import com.example.instagram_tracker.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowingRepository
        extends JpaRepository<Following, Long> {

    List<Following> findByProfile(Profile profile);

    Optional<Following> findByProfileAndUsername(
            Profile profile,
            String username
    );
}
