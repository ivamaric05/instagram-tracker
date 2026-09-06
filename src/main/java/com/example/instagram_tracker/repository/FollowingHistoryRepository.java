package com.example.instagram_tracker.repository;

import com.example.instagram_tracker.model.FollowingHistory;
import com.example.instagram_tracker.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowingHistoryRepository
        extends JpaRepository<FollowingHistory, Long> {

    List<FollowingHistory> findByProfile(Profile profile);
}
