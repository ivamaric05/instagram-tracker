package com.example.instagram_tracker.repository;

import com.example.instagram_tracker.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository
        extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUsername(String username);

    boolean existsByUsername(String username);
}
