package com.example.instagram_tracker.repository;

import com.example.instagram_tracker.model.FollowerHistory;
import com.example.instagram_tracker.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerHistoryRepository
        extends JpaRepository<FollowerHistory, Long> {

    List<FollowerHistory> findByProfileOrderByCheckedAtAsc(
            Profile profile
    );
}
