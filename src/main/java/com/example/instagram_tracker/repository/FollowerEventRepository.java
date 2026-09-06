package com.example.instagram_tracker.repository;

import com.example.instagram_tracker.model.FollowerEvent;
import com.example.instagram_tracker.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerEventRepository
        extends JpaRepository<FollowerEvent, Long> {

    List<FollowerEvent> findByProfileOrderByDetectedAtDesc(
            Profile profile
    );
}
